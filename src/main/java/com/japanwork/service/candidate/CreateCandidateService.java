package com.japanwork.service.candidate;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.candidate_translation.CandidateTranslationRepository;
import com.japanwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class CreateCandidateService {
    @Autowired
    private UserService userService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateTranslationRepository candidateTranslationRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public Candidate perform(CandidatePersonalRequest candidatePersonalRequest, User user, City city,
            District district, Language language) {
        Timestamp currentDateTime = CommonFunction.getCurrentDateTime();
        Candidate candidate = new Candidate();
        candidate.setUser(user);
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
        candidate.setStatusInfo(1);
        candidate.setCreatedAt(currentDateTime);
        candidate.setUpdatedAt(currentDateTime);
        Candidate candidateResult = candidateRepository.save(candidate);

        CandidateTranslation candidateTranslation = new CandidateTranslation();
        candidateTranslation.setLanguage(language);
        candidateTranslation.setCandidate(candidateResult);
        candidateTranslation.setFullName(candidatePersonalRequest.getFullName());
        candidateTranslation.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
        candidateTranslation.setIntroduction(candidatePersonalRequest.getIntroduction());
        candidateTranslation.setCreatedAt(currentDateTime);
        candidateTranslation.setUpdatedAt(currentDateTime);
        CandidateTranslation candidateTranslationResult = candidateTranslationRepository.save(candidateTranslation);

        userService.changePropertyId(user.getId(), candidateResult.getId());
        return candidateResult;
    }
}
