package com.japanwork.service.job_service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobTranslationRepository jobTranslationRepository;

    @Transactional(
            isolation   = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Job perform(
            JobRequest jobRequest,
            Company company,
            Business business,
            Contract contract,
            Level level,
            City city,
            District district,
            Language language
    ) {
        Job job = new Job();
        job.setCompany(company);
        job.setBusinesses(business);
        job.setContract(contract);
        job.setLevel(level);
        job.setCity(city);
        job.setDistrict(district);
        job.setApplicationDeadline(jobRequest.getApplicationDeadline());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        job.setCreatedAt(CommonFunction.getCurrentDateTime());
        job.setUpdatedAt(CommonFunction.getCurrentDateTime());
        //Remove these lines later
        //Begin
        job.setName(jobRequest.getName());
        job.setAddress(jobRequest.getAddress());
        job.setDesc(jobRequest.getDesc());
        job.setRequiredEducation(jobRequest.getRequiredEducation());
        job.setRequiredExperience(jobRequest.getRequiredExperience());
        job.setRequiredLanguage(jobRequest.getRequiredLanguage());
        job.setBenefits(jobRequest.getBenefits());
        job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
        //End
        Job jobResult = jobRepository.save(job);

        JobTranslation jobTranslation = new JobTranslation();
        jobTranslation.setJob(jobResult);
        jobTranslation.setLanguage(language);
        jobTranslation.setName(jobRequest.getName());
        jobTranslation.setAddress(jobRequest.getAddress());
        jobTranslation.setDescription(jobRequest.getDesc());
        jobTranslation.setRequiredEducation(jobRequest.getRequiredEducation());
        jobTranslation.setRequiredExperience(jobRequest.getRequiredExperience());
        jobTranslation.setRequiredLanguage(jobRequest.getRequiredLanguage());
        jobTranslation.setBenefit(jobRequest.getBenefits());
        jobTranslation.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
        JobTranslation jobTranslationResult = jobTranslationRepository.save(jobTranslation);

        return jobResult;
    }
}
