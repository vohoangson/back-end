package com.japanwork.repository.candidate_translation;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;

public interface CandidateTranslationRepository extends JpaRepository<CandidateTranslation, UUID> {
    public CandidateTranslation findByCandidateAndLanguage(Candidate candidate, Language language);
}
