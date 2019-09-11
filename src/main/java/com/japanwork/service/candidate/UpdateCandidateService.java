package com.japanwork.service.candidate;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.candidate_translation.CandidateTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class UpdateCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired CandidateTranslationRepository candidateTranslationRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public Candidate perform(CandidatePersonalRequest candidatePersonalRequest, Candidate candidate,
                             City city, District district, Language language) {
        Timestamp currentDateTime = CommonFunction.getCurrentDateTime();

        candidate.setFullName(candidatePersonalRequest.getFullName());
        candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
        candidate.setGender(candidatePersonalRequest.getGender());
        candidate.setMarital(candidatePersonalRequest.getMarital());
        candidate.setResidentalCity(city);
        candidate.setResidentalDistrict(district);
        candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
        candidate.setAvatar(candidatePersonalRequest.getAvatar());
        candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
        candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
        candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        candidate.setUpdatedAt(currentDateTime);
        Candidate result = candidateRepository.save(candidate);

        CandidateTranslation candidateTranslation = candidateTranslationRepository.findByCandidateAndLanguage(candidate, language);
        candidateTranslation.setFullName(candidatePersonalRequest.getFullName());
        candidateTranslation.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
        candidateTranslation.setIntroduction(candidatePersonalRequest.getIntroduction());
        candidateTranslation.setUpdatedAt(currentDateTime);
        CandidateTranslation resultCandidateTranslation = candidateTranslationRepository.save(candidateTranslation);

        return result;
    }
}
