package com.japanwork.repository.request_translation;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;

public interface RequestTranslationRepository extends JpaRepository<RequestTranslation, UUID>{
	public RequestTranslation findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
			UUID ownerId, UUID objectTableId, String objectTableType, Language language, Timestamp deletedAt);
	public RequestTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<RequestTranslation> findAllByObjectableIdAndObjectableTypeAndDeletedAt(UUID objectTableId, String objectTableType, Timestamp deletedAt);
}
