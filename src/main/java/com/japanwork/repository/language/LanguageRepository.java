package com.japanwork.repository.language;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.japanwork.model.Language;

public interface LanguageRepository extends JpaRepository<Language, UUID>{
    public Language findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
    public List<Language> findAllByDeletedAt(Timestamp deletedAt);
    @Query("SELECT l.id FROM Language l")
    public Set<UUID> findIdByDeletedAt(Timestamp deletedAt);
}
