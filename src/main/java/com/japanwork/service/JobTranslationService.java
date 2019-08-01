package com.japanwork.service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import com.japanwork.exception.ServerError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class JobTranslationService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JobTranslationRepository jobTranslationRepository;

    @Autowired
    private UserService userService;

    public JobTranslation save(JobTranslationRequest jobTranslationRequest) throws ServerError {
        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            JobTranslation jobTranslation = new JobTranslation();

            jobTranslation.setJob(new Job(jobTranslationRequest.getJobId()));
            jobTranslation.setTranslator(new Translator(jobTranslationRequest.getTranslatorId()));
            jobTranslation.setLanguage(new Language(jobTranslationRequest.getLanguageId()));
            jobTranslation.setName(jobTranslationRequest.getName());
            jobTranslation.setAddress(jobTranslationRequest.getAddress());
            jobTranslation.setDescription(jobTranslationRequest.getDescription());
            jobTranslation.setRequiredEducation(jobTranslationRequest.getRequiredEducation());
            jobTranslation.setRequiredExperience(jobTranslationRequest.getRequired_experience());
            jobTranslation.setRequiredLanguage(jobTranslationRequest.getRequiredLanguage());
            jobTranslation.setJapaneseLevelRequirement(jobTranslationRequest.getJapaneseLevelRequirement());
            jobTranslation.setBenefit(jobTranslationRequest.getBenefit());
            jobTranslation.setCreatedAt(timestamp);
            jobTranslation.setUpdatedAt(timestamp);

            JobTranslation result = jobTranslationRepository.save(jobTranslation);
            return result;
        } catch (Exception e) {
            throw new ServerError(MessageConstant.CREATED_SUCCESS);
        }
    }
}
