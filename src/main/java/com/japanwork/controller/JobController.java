package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.japanwork.model.*;
import com.japanwork.payload.response.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.payload.request.JobFilterRequest;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.job_service.CreateJobService;
import com.japanwork.service.job_service.IndexJobByCompanyService;
import com.japanwork.service.job_service.IndexJobService;
import com.japanwork.service.job_service.UpdateJobService;
import com.japanwork.support.CommonSupport;

@Controller
public class JobController {
    @Autowired(required = false)
    private IndexJobService indexJobService;

    @Autowired(required = false)
    private CreateJobService createJobService;

    @Autowired(required = false)
    private UpdateJobService updateJobService;

    @Autowired(required = false)
    private IndexJobByCompanyService indexJobByCompanyService;

    @Autowired(required = false)
    private JobRepository jobRepository;

    @Autowired
    private CommonSupport commonSupport;

	@GetMapping(UrlConstant.URL_JOBS)
	@ResponseBody
	public ResponseDataAPI index(
            @RequestParam(name = "language") String languageCode,
	        @RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "job_name") String jobName,
			@RequestParam(defaultValue = "", name = "company_name") String companyName,
			@RequestParam(defaultValue = "", name = "business_ids") String businessIds,
			@RequestParam(defaultValue = "", name = "contract_ids") String contractIds,
			@RequestParam(defaultValue = "", name = "level_ids") String levelIds,
			@RequestParam(defaultValue = "", name = "city_ids") String cityIds,
			@RequestParam(defaultValue = "0", name = "min_salary") int minSalary,
			@RequestParam(defaultValue = "", name = "post_time") String postTime) {
	    Language language = commonSupport.loadLanguage(languageCode);

	    JobFilterRequest jobFilterRequest = new JobFilterRequest();
		jobFilterRequest.setJobName(jobName);
		jobFilterRequest.setCompanyName(companyName);
		jobFilterRequest.setBusinessIds(CommonFunction.listParam(businessIds));
		jobFilterRequest.setCityIds(CommonFunction.listParam(cityIds));
		jobFilterRequest.setContractIds(CommonFunction.listParam(contractIds));
		jobFilterRequest.setLevelIds(CommonFunction.listParam(levelIds));
		jobFilterRequest.setMinSalary(minSalary);
		jobFilterRequest.setPostTime(postTime);

		ResponseDataAPI response = indexJobService.perform(jobFilterRequest, page, paging, language);
		return response;
	}

    @GetMapping(UrlConstant.URL_COMPANY_JOB)
    @ResponseBody
    public ResponseDataAPI indexByCompany(
            @PathVariable UUID id,
            @RequestParam(name = "language") String languageCode,
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "25", name = "paging") int paging) {

        Language language = commonSupport.loadLanguage(languageCode);
        Company company = commonSupport.loadCompanyById(id);
        CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);
        ResponseDataAPI response = indexJobByCompanyService.perform(page, paging, company, companyTranslation, language);

        return response;
    }

    @PostMapping(UrlConstant.URL_JOBS)
    @ResponseBody
    public ResponseDataAPI create(
            @Valid @RequestBody JobRequest jobRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        Language language = commonSupport.loadUserById(userPrincipal.getId()).getCountry().getLanguage();
        Company company   = commonSupport.loadCompanyByUser(userPrincipal.getId());
        Business business = commonSupport.loadBusiness(jobRequest.getBusinessId());
        Contract contract = commonSupport.loadContract(jobRequest.getContractId());
        Level level       = commonSupport.loadLevel(jobRequest.getLevelId());
        City city         = commonSupport.loadCity(jobRequest.getCityId());
        District district = commonSupport.loadDistrict(jobRequest.getDistrictId());

        Job job = createJobService.perform(
                jobRequest,
                company,
                business,
                contract,
                level,
                city,
                district,
                language
        );

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                job.getId(),
                ""
        );
    }

    @GetMapping(UrlConstant.URL_JOB)
    @ResponseBody
    public ResponseDataAPI show(
            @PathVariable UUID id,
            @RequestParam(name = "language", defaultValue = "") String languageCode) {
        Job job                               = commonSupport.loadJobById(id);
        Company company                       = job.getCompany();
        Language language = null;
        if(languageCode.isEmpty()) {
        	language = company.getUser().getCountry().getLanguage();
        } else {
        	language = commonSupport.loadLanguage(languageCode);
        }
                             
        JobTranslation jobTranslation         = commonSupport.loadJobTranslation(job, language);
        CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);

        CompanyResponse companyResponse = new CompanyResponse().companyMainSerializer(company, companyTranslation);
        JobResponse jobResponse         = new JobResponse().jobFullSerializer(job, jobTranslation, companyResponse);

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                jobResponse,
                ""
        );
    }

    @PatchMapping(UrlConstant.URL_JOB)
    @ResponseBody
    public ResponseDataAPI update(
            @PathVariable UUID id,
            @Valid @RequestBody JobRequest jobRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        Job job = commonSupport.loadJobById(id);

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        Language language = commonSupport.loadUserById(userPrincipal.getId()).getCountry().getLanguage();
        Business business = commonSupport.loadBusiness(jobRequest.getBusinessId());
        Contract contract = commonSupport.loadContract(jobRequest.getContractId());
        Level level       = commonSupport.loadLevel(jobRequest.getLevelId());
        City city         = commonSupport.loadCity(jobRequest.getCityId());
        District district = commonSupport.loadDistrict(jobRequest.getDistrictId());

        Job result = updateJobService.perform(
                jobRequest,
                job,
                business,
                contract,
                level,
                city,
                district,
                language
        );

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                result.getId(),
                ""
        );
    }

    @DeleteMapping(UrlConstant.URL_JOB)
    @ResponseBody
    public ResponseDataAPI destroy(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
        Job job = commonSupport.loadJobById(id);

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        job.setDeletedAt(CommonFunction.getCurrentDateTime());
        jobRepository.save(job);

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );

    }

    @PatchMapping(UrlConstant.URL_JOB_UNDELETE)
    @ResponseBody
    public ResponseDataAPI unDestroy(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
        Job job = commonSupport.loadJobById(id);

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        job.setDeletedAt(null);
        jobRepository.save(job);

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
    }
}
