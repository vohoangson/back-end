package com.japanwork.service.candidate;

import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.response.AcademyResponse;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.payload.response.ExperienceResponse;
import com.japanwork.payload.response.LanguageCertificateResponse;
import com.japanwork.repository.candidate_translation.CandidateTranslationRepository;
import com.japanwork.support.CandidateSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CandidateBaseService {
    @Autowired
    CandidateSupport candidateSupport;

    @Autowired
    private CandidateTranslationRepository candidateTranslationRepository;

    public CandidateResponse getCandidateResponse(Candidate candidate, Language language) {
//        CandidateTranslation candidateTranslation = candidateTranslationRepository.findByCandidateAndLanguage(candidate, language);
        Set<AcademyResponse> academyResponses = candidateSupport.getAcademyResponses(candidate.getAcademies());
        Set<ExperienceResponse> experienceResponses = candidateSupport.getExperienceResponses(candidate.getExperiences());
        Set<LanguageCertificateResponse> languageCertificateResponses = candidateSupport.getLanguageCertificateResponses(candidate.getLanguageCertificates());

        CandidateResponse candidateResponse = new CandidateResponse();

        return candidateResponse.candidateFullSerialzer(candidate,
                academyResponses, experienceResponses, languageCertificateResponses);
    }
}
