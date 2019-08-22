package com.japanwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.repository.CandidateTranslation.CandidateTranslationRepository;

@Service
public class CandidateTranslationService {
    @Autowired
    private CandidateTranslationRepository candidateTranslationRepository;

    public CandidateTranslation save(
            Candidate candidate,
            Language language,
            CandidateTranslationRequest candidateTranslationRequest
    ) throws ServerError {
        try {
            CandidateTranslation candidateTranslation = new CandidateTranslation();
            candidateTranslation.setCandidate(candidate);
            candidateTranslation.setLanguage(language);
            candidateTranslation.setFullName(candidateTranslationRequest.getFullName());
            candidateTranslation.setResidentalAddres(candidateTranslationRequest.getResidentalAddress());
            candidateTranslation.setIntroduction(candidateTranslationRequest.getIntroduction());
            candidateTranslation.setExpectedWorkingAddress(candidateTranslationRequest.getExpectedWorkingAddress());
            candidateTranslation.setUpdatedAt(null);
            candidateTranslation.setCreatedAt(CommonFunction.dateTimeNow());

            CandidateTranslation result = candidateTranslationRepository.save(candidateTranslation);
            return result;
        } catch (Exception e) {
            throw new ServerError(MessageConstant.INTERNAL_SERVER_ERROR);
        }
    }
}
