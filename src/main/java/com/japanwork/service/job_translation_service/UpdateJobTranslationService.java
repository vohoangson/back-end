package com.japanwork.service.job_translation_service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.JobTranslation;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateJobTranslationService {
    @Autowired
    private JobTranslationRepository jobTranslationRepository;

    public JobTranslation perform(
            JobTranslation jobTranslation,
            JobTranslationRequest jobTranslationRequest) {

        jobTranslation.setName(jobTranslationRequest.getName());
        jobTranslation.setAddress(jobTranslationRequest.getAddress());
        jobTranslation.setDescription(jobTranslationRequest.getDescription());
        jobTranslation.setRequiredEducation(jobTranslationRequest.getRequiredEducation());
        jobTranslation.setRequiredExperience(jobTranslationRequest.getRequiredExperience());
        jobTranslation.setRequiredLanguage(jobTranslationRequest.getRequiredLanguage());
        jobTranslation.setJapaneseLevelRequirement(jobTranslationRequest.getJapaneseLevelRequirement());
        jobTranslation.setBenefit(jobTranslationRequest.getBenefit());
        jobTranslation.setCreatedAt(CommonFunction.getCurrentDateTime());
        jobTranslation.setUpdatedAt(CommonFunction.getCurrentDateTime());

        JobTranslation result = jobTranslationRepository.save(jobTranslation);
        return result;
    }
}
