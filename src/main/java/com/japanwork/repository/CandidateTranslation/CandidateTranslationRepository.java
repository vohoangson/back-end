package com.japanwork.repository.CandidateTranslation;

import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Translator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface CandidateTranslationRepository extends JpaRepository<CandidateTranslation, UUID> {
    public CandidateTranslation findByCandidateAndTranslatorAndDeletedAt(
            Candidate candidate,
            Translator translator,
            Timestamp deletedAt
    );
}
