package com.japanwork.repository.job_translation;

import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface JobTranslationRepository extends JpaRepository<JobTranslation, UUID> {
    public JobTranslation findByJobAndLanguageAndDeletedAt(Job job, Language language, Timestamp deletedAt);
}
