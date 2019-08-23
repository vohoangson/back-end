package com.japanwork.repository.academy_translation;

import com.japanwork.model.Academy;
import com.japanwork.model.AcademyTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface AcademyTranslationRepository extends JpaRepository<AcademyTranslation, UUID> {
    public AcademyTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
