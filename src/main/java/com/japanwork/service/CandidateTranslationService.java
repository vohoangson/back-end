package com.japanwork.service;

import com.japanwork.model.*;
import com.japanwork.payload.request.AcademyTranslationRequest;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.payload.request.ExperienceTranslationRequest;
import com.japanwork.repository.candidate_translation.CandidateTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CandidateTranslationService {
    @Autowired
    private CandidateTranslationRepository candidateTranslationRepository;

    @Autowired
    private AcademyTranslationService academyTranslationService;

    @Autowired
    private ExperienceTranslationService experienceTranslationService;

    @Transactional(
            isolation   = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public void save(
            Candidate candidate,
            Language language,
            CandidateTranslationRequest candidateTranslationRequest
    ) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        CandidateTranslation candidateTranslation = new CandidateTranslation();
        candidateTranslation.setCandidate(candidate);
        candidateTranslation.setLanguage(language);
        candidateTranslation.setFullName(candidateTranslationRequest.getFullName());
        candidateTranslation.setResidentalAddres(candidateTranslationRequest.getResidentalAddress());
        candidateTranslation.setIntroduction(candidateTranslationRequest.getIntroduction());
        candidateTranslation.setUpdatedAt(timestamp);
        candidateTranslation.setCreatedAt(timestamp);
        CandidateTranslation result = candidateTranslationRepository.save(candidateTranslation);

        List<AcademyTranslationRequest> academyTranslationRequests = candidateTranslationRequest.getAcademyTranslationRequests();
        if(!academyTranslationRequests.isEmpty()) {
            for (AcademyTranslationRequest academyTranslationRequest : academyTranslationRequests) {
                academyTranslationService.save(result, language, academyTranslationRequest);
            }
        }

        List<ExperienceTranslationRequest> experienceTranslationRequests = candidateTranslationRequest.getExperienceTranslationRequests();
        if(!experienceTranslationRequests.isEmpty()) {
            for (ExperienceTranslationRequest experienceTranslationRequest : experienceTranslationRequests) {
                experienceTranslationService.save(result, language, experienceTranslationRequest);
            }
        }
    }
}
