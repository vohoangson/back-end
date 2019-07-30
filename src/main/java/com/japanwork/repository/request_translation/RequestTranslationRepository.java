package com.japanwork.repository.request_translation;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;

public interface RequestTranslationRepository extends JpaRepository<RequestTranslation, UUID>{
	public RequestTranslation findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
			UUID ownerId, UUID objectTableId, String objectTableType, Language language, Timestamp deletedAt);
	public RequestTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public Page<RequestTranslation> findAllByTranslatorAndDeletedAt( Pageable page, Translator translator, Timestamp deletedAt);
	public Page<RequestTranslation> findAllByOwnerIdAndDeletedAt( Pageable page, UUID objecttableId, Timestamp deletedAt);
}
