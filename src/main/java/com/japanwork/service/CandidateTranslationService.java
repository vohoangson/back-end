package com.japanwork.service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.repository.CandidateTranslation.CandidateTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

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
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());

            CandidateTranslation candidateTranslation = new CandidateTranslation();
            candidateTranslation.setCandidate(candidate);
            candidateTranslation.setLanguage(language);
            candidateTranslation.setFullName(candidateTranslationRequest.getFullName());
            candidateTranslation.setResidentalAddres(candidateTranslationRequest.getResidentalAddress());
            candidateTranslation.setIntroduction(candidateTranslationRequest.getIntroduction());
            candidateTranslation.setExpectedWorkingAddress(candidateTranslationRequest.getExpectedWorkingAddress());
            candidateTranslation.setUpdatedAt(timestamp);
            candidateTranslation.setCreatedAt(timestamp);

            CandidateTranslation result = candidateTranslationRepository.save(candidateTranslation);
            return result;
        } catch (Exception e) {
            throw new BadRequestException(MessageConstant.CREATED_FAILED);
        }
    }
}
