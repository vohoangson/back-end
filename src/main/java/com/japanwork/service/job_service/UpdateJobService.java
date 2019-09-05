package com.japanwork.service.job_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.District;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.Level;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.job_translation.JobTranslationRepository;

@Service
public class UpdateJobService {
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
            Job job,
            Business business,
            Contract contract,
            Level level,
            City city,
            District district,
            Language language
    ) throws ForbiddenException
    {
        job.setName(jobRequest.getName());
        job.setBusinesses(business);
        job.setContract(contract);
        job.setLevel(level);
        job.setCity(city);
        job.setDistrict(district);
        job.setAddress(jobRequest.getAddress());
        job.setDesc(jobRequest.getDesc());
        job.setRequiredEducation(jobRequest.getRequiredEducation());
        job.setRequiredExperience(jobRequest.getRequiredExperience());
        job.setRequiredLanguage(jobRequest.getRequiredLanguage());
        job.setBenefits(jobRequest.getBenefits());
        job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
        job.setApplicationDeadline(jobRequest.getApplicationDeadline());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        job.setUpdatedAt(CommonFunction.getCurrentDateTime());
        Job result = jobRepository.save(job);

        JobTranslation jobTranslation = jobTranslationRepository.findByJobAndLanguageAndDeletedAt(
                result,
                language,
                null
        );
        jobTranslation.setLanguage(language);
        jobTranslation.setName(jobRequest.getName());
        jobTranslation.setAddress(jobRequest.getAddress());
        jobTranslation.setDescription(jobRequest.getDesc());
        jobTranslation.setRequiredEducation(jobRequest.getRequiredEducation());
        jobTranslation.setRequiredExperience(jobRequest.getRequiredExperience());
        jobTranslation.setRequiredLanguage(jobRequest.getRequiredLanguage());
        jobTranslation.setBenefit(jobRequest.getBenefits());
        JobTranslation jobTranslationResult = jobTranslationRepository.save(jobTranslation);

        return result;
    }
}
