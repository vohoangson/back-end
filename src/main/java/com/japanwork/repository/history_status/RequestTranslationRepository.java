package com.japanwork.repository.history_status;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;

public interface RequestTranslationRepository extends JpaRepository<RequestTranslation, UUID>{
	public RequestTranslation findByOwnerIdAndObjectTableIdAndObjectTableTypeAndLanguageAndDeletedAt(
			UUID ownerId, UUID objectTableId, String objectTableType, Language language, Timestamp deletedAt);
	public RequestTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
