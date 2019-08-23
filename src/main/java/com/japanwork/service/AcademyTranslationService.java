package com.japanwork.service;

import com.japanwork.model.Academy;
import com.japanwork.model.AcademyTranslation;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.AcademyTranslationRequest;
import com.japanwork.repository.academy_translation.AcademyTranslationRepository;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AcademyTranslationService {
    @Autowired
    private CommonSupport commonSupport;

    @Autowired
    private AcademyTranslationRepository academyTranslationRepository;

    public void save(
            CandidateTranslation candidateTranslation,
            Language language,
            AcademyTranslationRequest academyTranslationRequest
    ) {
        Academy academy = commonSupport.loadAcademy(academyTranslationRequest.getAcademyId());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        AcademyTranslation academyTranslation = new AcademyTranslation();
        academyTranslation.setAcademy(academy);
        academyTranslation.setCandidateTranslation(candidateTranslation);
        academyTranslation.setLanguage(language);
        academyTranslation.setAcademyCenterName(academyTranslationRequest.getAcademyCenterName());
        academyTranslation.setMajorName(academyTranslationRequest.getMajorName());
        academyTranslation.setCreatedAt(timestamp);
        academyTranslation.setUpdatedAt(timestamp);

        academyTranslationRepository.save(academyTranslation);
    }
}
