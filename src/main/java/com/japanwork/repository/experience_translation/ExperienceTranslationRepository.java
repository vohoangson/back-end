package com.japanwork.repository.experience_translation;

import com.japanwork.model.ExperienceTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface ExperienceTranslationRepository extends JpaRepository<ExperienceTranslation, UUID> {
    public ExperienceTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
