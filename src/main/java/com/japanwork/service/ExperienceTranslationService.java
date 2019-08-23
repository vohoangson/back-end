package com.japanwork.service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Experience;
import com.japanwork.model.ExperienceTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.ExperienceTranslationRequest;
import com.japanwork.repository.experience_translation.ExperienceTranslationRepository;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ExperienceTranslationService {
    @Autowired
    private CommonSupport commonSupport;

    @Autowired
    private ExperienceTranslationRepository experienceTranslationRepository;

    public void save(
            CandidateTranslation candidateTranslation,
            Language language,
            ExperienceTranslationRequest experienceTranslationRequest
    ) {
        Experience experience = commonSupport.loadExperience(experienceTranslationRequest.getExperienceId());

        ExperienceTranslation experienceTranslation = new ExperienceTranslation();
        experienceTranslation.setExperience(experience);
        experienceTranslation.setCandidateTranslation(candidateTranslation);
        experienceTranslation.setLanguage(language);
        experienceTranslation.setOrganization(experienceTranslationRequest.getOrganization());
        experienceTranslation.setDescription(experienceTranslationRequest.getDescription());
        experienceTranslation.setCreatedAt(CommonFunction.getCurrentDateTime());
        experienceTranslation.setUpdatedAt(CommonFunction.getCurrentDateTime());

        experienceTranslationRepository.save(experienceTranslation);
    }
}
