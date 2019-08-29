package com.japanwork.service.job_service;

import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowService {
    @Autowired
    private JobTranslationRepository jobTranslationRepository;
    
    @Autowired
    private CommonSupport commonSupport;

    public JobResponse perform(Job job, Language language) {
        JobTranslation jobTranslation = jobTranslationRepository.findByJobAndLanguageAndDeletedAt(
                job,
                language,
                null
        );
        
        CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(job.getCompany(), language);
        JobResponse jobResponse = new JobResponse();
        return jobResponse.jobFullSerializer(
        		job, 
        		jobTranslation, 
        		new CompanyResponse().companyMainSerializer(job.getCompany(), companyTranslation));
    }
}
