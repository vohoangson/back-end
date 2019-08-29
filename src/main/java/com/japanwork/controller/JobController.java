package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.amazonaws.services.glue.model.JobCommand;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.*;
import com.japanwork.payload.request.JobFilterRequest;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.service.job_service.*;
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
import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.support.CommonSupport;

@Controller
public class JobController {
    @Autowired(required = false)
    private IndexService indexService;

    @Autowired(required = false)
    private ShowService showService;

    @Autowired(required = false)
    private CreateService createService;

    @Autowired(required = false)
    private UpdateService updateService;

    @Autowired(required = false)
    private IndexByCompanyService indexByCompanyService;

    @Autowired(required = false)
    private JobRepository jobRepository;

    @Autowired
    private CommonSupport commonSupport;

	@GetMapping(UrlConstant.URL_JOBS)
	@ResponseBody
	public ResponseDataAPI index(
            @RequestParam(name = "language") String language_code,
	        @RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "job_name") String jobName,
			@RequestParam(defaultValue = "", name = "company_name") String companyName,
			@RequestParam(defaultValue = "", name = "business_ids") String businessIds,
			@RequestParam(defaultValue = "", name = "contract_ids") String contractIds,
			@RequestParam(defaultValue = "", name = "level_ids") String levelIds,
			@RequestParam(defaultValue = "", name = "city_ids") String cityIds,
			@RequestParam(defaultValue = "0", name = "min_salary") int minSalary,
			@RequestParam(defaultValue = "", name = "post_time") String postTime
    ) {
	    Language language = commonSupport.loadLanguage(language_code);

	    JobFilterRequest jobFilterRequest = new JobFilterRequest();
		jobFilterRequest.setJobName(jobName);
		jobFilterRequest.setCompanyName(companyName);
		jobFilterRequest.setBusinessIds(CommonFunction.listParam(businessIds));
		jobFilterRequest.setCityIds(CommonFunction.listParam(cityIds));
		jobFilterRequest.setContractIds(CommonFunction.listParam(contractIds));
		jobFilterRequest.setLevelIds(CommonFunction.listParam(levelIds));
		jobFilterRequest.setMinSalary(minSalary);
		jobFilterRequest.setPostTime(postTime);

		ResponseDataAPI response = indexService.perform(jobFilterRequest, page, paging, language);
		return response;
	}

    @GetMapping(UrlConstant.URL_COMPANY_JOB)
    @ResponseBody
    public ResponseDataAPI indexByCompany(
            @PathVariable UUID id,
            @RequestParam(name = "language") String language_code,
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "25", name = "paging") int paging
    ) {
        Language language = commonSupport.loadLanguage(language_code);

        ResponseDataAPI response = indexByCompanyService.perform(page, paging, id, language);

        return response;
    }

    @PostMapping(UrlConstant.URL_JOBS)
    @ResponseBody
    public ResponseDataAPI create(
            @RequestParam(name = "language") String language_code,
            @Valid @RequestBody JobRequest jobRequest,
            @CurrentUser UserPrincipal userPrincipal
    ) {
	    Language language = commonSupport.loadLanguage(language_code);
        Company company   = commonSupport.loadCompanyByUser(userPrincipal.getId());
        Business business = commonSupport.loadBusiness(jobRequest.getBusinessId());
        Contract contract = commonSupport.loadContract(jobRequest.getContractId());
        Level level       = commonSupport.loadLevel(jobRequest.getLevelId());
        City city         = commonSupport.loadCity(jobRequest.getCityId());
        District district = commonSupport.loadDistrict(jobRequest.getDistrictId());

        Job job = createService.perform(
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
            @RequestParam(name = "language") String language_code
    ){
        Language language = commonSupport.loadLanguage(language_code);
        Job job           = commonSupport.loadJobById(id);

        JobResponse jobResponse = showService.perform(job, language);

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                jobResponse,
                ""
        );
    }

    @PatchMapping(UrlConstant.URL_JOB)
    @ResponseBody
    public ResponseDataAPI update(
            @Valid @RequestBody JobRequest jobRequest,
            @PathVariable UUID id,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        Job job = commonSupport.loadJobById(id);

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        Business business = commonSupport.loadBusiness(jobRequest.getBusinessId());
        Contract contract = commonSupport.loadContract(jobRequest.getContractId());
        Level level       = commonSupport.loadLevel(jobRequest.getLevelId());
        City city         = commonSupport.loadCity(jobRequest.getCityId());
        District district = commonSupport.loadDistrict(jobRequest.getDistrictId());

        Job result = updateService.perform(
                jobRequest,
                job,
                business,
                contract,
                level,
                city,
                district
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
