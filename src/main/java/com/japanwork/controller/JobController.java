package com.japanwork.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

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
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Contract;
import com.japanwork.model.District;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.Level;
import com.japanwork.model.QJob;
import com.japanwork.model.QJobTranslation;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.job_service.CreateJobService;
import com.japanwork.service.job_service.IndexJobByCompanyService;
import com.japanwork.service.job_service.IndexJobService;
import com.japanwork.service.job_service.UpdateJobService;
import com.japanwork.support.CommonSupport;
import com.querydsl.core.BooleanBuilder;

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
			@RequestParam(defaultValue = "", name = "business_ids") Set<UUID> businessIds,
			@RequestParam(defaultValue = "", name = "contract_ids") Set<UUID> contractIds,
			@RequestParam(defaultValue = "", name = "level_ids") Set<UUID> levelIds,
			@RequestParam(defaultValue = "", name = "city_ids") Set<UUID> cityIds,
			@RequestParam(defaultValue = "0", name = "min_salary") int minSalary,
			@RequestParam(defaultValue = "", name = "post_time") String postTime) throws ParseException {
	    
		Language language = commonSupport.loadLanguage(languageCode);
	    
	    BooleanBuilder booleanBuilder = new BooleanBuilder();
		
	    QJob qjob = QJobTranslation.jobTranslation.job;
		if(!jobName.isEmpty()) {
			booleanBuilder.and(qjob.name.likeIgnoreCase("%"+jobName+"%"));
		}
		
		if(!companyName.isEmpty()) {
			booleanBuilder.and(qjob.company.name.likeIgnoreCase("%"+companyName+"%"));
		}
		
		if(businessIds.size() > 0) {
			booleanBuilder.and(qjob.businesses.id.in(businessIds));
		}
		
		if(contractIds.size() > 0) {
			booleanBuilder.and(qjob.contract.id.in(contractIds));
		}
		
		if(levelIds.size() > 0) {
			booleanBuilder.and(qjob.level.id.in(levelIds));
		}
		
		if(cityIds.size() > 0) {
			booleanBuilder.and(qjob.city.id.in(cityIds));
		}
		
		if(minSalary > 0) {
			booleanBuilder.and(qjob.maxSalary.goe(minSalary));
		}
		
		if(!postTime.isEmpty()) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(postTime);
			Timestamp timestamp = new Timestamp(date.getTime());
			booleanBuilder.and(QJobTranslation.jobTranslation.createdAt.goe(timestamp));
		}
		
		booleanBuilder.and(QJobTranslation.jobTranslation.language.id.eq(language.getId()));
		
		ResponseDataAPI response = indexJobService.perform(booleanBuilder.getValue(), page, paging, language);
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
