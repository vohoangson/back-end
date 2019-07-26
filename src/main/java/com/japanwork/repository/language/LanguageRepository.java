package com.japanwork.repository.language;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Language;

public interface LanguageRepository extends JpaRepository<Language, UUID>{
    public Language findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
    public List<Language> findAllByDeletedAt(Timestamp deletedAt);
}
