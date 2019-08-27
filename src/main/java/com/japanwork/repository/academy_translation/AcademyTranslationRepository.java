package com.japanwork.repository.academy_translation;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.AcademyTranslation;

public interface AcademyTranslationRepository extends JpaRepository<AcademyTranslation, UUID> {
    public AcademyTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
