package com.japanwork.repository.language;

import org.apache.commons.codec.language.bm.Lang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;
import java.sql.Timestamp;

import com.japanwork.model.Language;

public interface LanguageRepository extends JpaRepository<Language, UUID>{
    public Language findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
    public List<Language> findAllByDeletedAt(Timestamp deletedAt);
}
