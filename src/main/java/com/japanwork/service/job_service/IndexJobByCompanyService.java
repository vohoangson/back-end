package com.japanwork.service.job_service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
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

@Service
public class IndexJobByCompanyService {
    @Autowired
    private JobTranslationRepository jobTranslationRepository;
    
    public ResponseDataAPI perform(int page, int paging, Company company, CompanyTranslation companyTranslation, Language language) 
    		throws ResourceNotFoundException {
        try {
  	
            Page<JobTranslation> pages = jobTranslationRepository.findAllByJobCompanyAndLanguageAndDeletedAt(
            																			PageRequest.of(page-1, paging), 
            																			company, 
            																			language, 
            																			null);
            PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
            List<JobResponse> list = new ArrayList<JobResponse>();

            JobResponse jobResponse = new JobResponse();
            if(pages.getContent().size() > 0) {
                for (JobTranslation jobTranslation : pages.getContent()) {
                	
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
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
