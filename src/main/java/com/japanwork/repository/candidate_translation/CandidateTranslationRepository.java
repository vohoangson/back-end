package com.japanwork.repository.candidate_translation;

import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.Translator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface CandidateTranslationRepository extends JpaRepository<CandidateTranslation, UUID> {
    public CandidateTranslation findByCandidateAndLanguageAndDeletedAt(
            Candidate candidate,
            Language language,
            Timestamp deletedAt
    );
}
