package com.japanwork.service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.Translator;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.repository.CandidateTranslation.CandidateTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class CandidateTranslationService {
    @Autowired
    private CandidateTranslationRepository candidateTranslationRepository;

    public CandidateTranslation save(
            UUID id,
            CandidateTranslationRequest candidateTranslationRequest
    ) throws ServerError {
        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());

            CandidateTranslation candidateTranslation = new CandidateTranslation(
                    new Candidate(id),
                    new Language(candidateTranslationRequest.getLanguageId()),
                    candidateTranslationRequest.getFullName(),
                    candidateTranslationRequest.getResidentalAddress(),
                    candidateTranslationRequest.getIntroduction(),
                    candidateTranslationRequest.getExpectedWorkingAddress(),
                    timestamp,
                    timestamp
            );

            CandidateTranslation result = candidateTranslationRepository.save(candidateTranslation);
            return result;
        } catch (Exception e) {
            throw new ServerError(MessageConstant.CREATED_FAILED);
        }
    }
}
