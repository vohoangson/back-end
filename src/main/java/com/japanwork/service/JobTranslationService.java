package com.japanwork.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.repository.job_translation.JobTranslationRepository;

@Service
public class JobTranslationService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JobTranslationRepository jobTranslationRepository;

    public JobTranslation save(
            Job job,
            Language language,
            JobTranslationRequest jobTranslationRequest) throws ServerError {
        try {
            JobTranslation jobTranslation = new JobTranslation();
            jobTranslation.setJob(job);
            jobTranslation.setLanguage(language);
            jobTranslation.setName(jobTranslationRequest.getName());
            jobTranslation.setAddress(jobTranslationRequest.getAddress());
            jobTranslation.setDescription(jobTranslationRequest.getDescription());
            jobTranslation.setRequiredEducation(jobTranslationRequest.getRequiredEducation());
            jobTranslation.setRequiredExperience(jobTranslationRequest.getRequired_experience());
            jobTranslation.setRequiredLanguage(jobTranslationRequest.getRequiredLanguage());
            jobTranslation.setJapaneseLevelRequirement(jobTranslationRequest.getJapaneseLevelRequirement());
            jobTranslation.setBenefit(jobTranslationRequest.getBenefit());
            jobTranslation.setCreatedAt(CommonFunction.dateTimeNow());
            jobTranslation.setUpdatedAt(null);

            JobTranslation result = jobTranslationRepository.save(jobTranslation);
            return result;
        } catch (Exception e) {
            throw new ServerError(MessageConstant.JOB_TRANSLATE_CREATE_FAIL);
        }
    }
}
