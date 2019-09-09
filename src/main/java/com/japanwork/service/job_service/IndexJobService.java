package com.japanwork.service.job_service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import com.japanwork.support.CommonSupport;
import com.querydsl.core.types.Predicate;

@Service
public class IndexJobService {    
    @Autowired
    private JobTranslationRepository jobTranslationRepository;
    
    @Autowired
    private CommonSupport commonSupport;
    
    public ResponseDataAPI perform(
    		Predicate predicate,
            int page,
            int paging,
            Language language
    ) throws ResourceNotFoundException
    {
    	
    	Page<JobTranslation> pages = jobTranslationRepository.findAll(predicate, PageRequest.of(page-1, paging, Sort.by("createdAt").descending()));
    	PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
    	
    	List<JobResponse> list = new ArrayList<JobResponse>();

        JobResponse jobResponse = new JobResponse();
        if(pages.getContent().size() > 0) {
            for (JobTranslation jobTranslation : pages.getContent()) {
            	Company company = jobTranslation.getJob().getCompany();
            	CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);
                list.add(jobResponse.jobFullSerializer(
                		jobTranslation.getJob(),
                        jobTranslation,
                        new CompanyResponse().companyMainSerializer(company, companyTranslation)
                ));
            }
        }
    	
    	return new ResponseDataAPI(
              CommonConstant.ResponseDataAPIStatus.SUCCESS,
              list,
              pageInfo
      );
    }
}
