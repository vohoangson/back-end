package com.japanwork.repository.request_translation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;

public interface RequestTranslationRepository extends JpaRepository<RequestTranslation, UUID>{
	public RequestTranslation findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
			UUID ownerId, UUID objectTableId, String objectTableType, Language language, Timestamp deletedAt);
	public RequestTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public RequestTranslation findByObjectableIdAndObjectableTypeAndDeletedAt(UUID id, String type,Timestamp deletedAt);
	public List<RequestTranslation> findAllByObjectableIdAndObjectableTypeAndDeletedAt(UUID objectTableId, String objectTableType, 
			Timestamp deletedAt);
	
	@Query("SELECT DISTINCT r "
		+ "	FROM RequestTranslation r "
		+ "	LEFT JOIN Company c "
		+ "		ON c.id = r.ownerId "
		+ "		AND r.objectableType != 'REQUEST_CANDIDATE_TRANSLATION' "
		+ "	WHERE r.deletedAt is null "
		+ "		AND c.name LIKE %:name% "
		+ "		AND r.objectableType IN :objectableTypes "
		+ "		AND r.language.id IN :languageIds "
		+ " 	AND r.createdAt >= :postTime "
		+ " 	AND r.ownerId = :ownerId "
		+ "	ORDER BY r.createdAt DESC ")
	public Page<RequestTranslation> findAllByCompany(Pageable page, @Param("name") String name, 
			@Param("objectableTypes") Set<String> objectableTypes, @Param("languageIds") Set<UUID> languageIds, 
			@Param("postTime") Date postTime, @Param("ownerId") UUID ownerId ); 
	
	@Query("SELECT DISTINCT r "
			+ "	FROM RequestTranslation r "
			+ "	LEFT JOIN Candidate c "
			+ "		ON c.id = r.ownerId "
			+ "		AND r.objectableType = 'REQUEST_CANDIDATE_TRANSLATION' "
			+ "	WHERE r.deletedAt is null "
			+ "		AND c.fullName LIKE %:name% "
			+ "		AND r.language.id IN :languageIds "
			+ " 	AND r.createdAt >= :postTime "
			+ " 	AND r.ownerId = :ownerId "
			+ "	ORDER BY r.createdAt DESC ")
	public Page<RequestTranslation> findAllByCandidate(Pageable page, @Param("name") String name, 
			@Param("languageIds") Set<UUID> languageIds, @Param("postTime") Date postTime, @Param("ownerId") UUID ownerId );
	
	@Query("SELECT rt "
			+ "FROM RequestTranslation rt "
			+ "LEFT JOIN Company co "
			+ "		ON co.id = rt.ownerId "
			+ "		AND rt.objectableType != 'REQUEST_CANDIDATE_TRANSLATION' "
			+ "LEFT JOIN Candidate ca "
			+ "		ON ca.id = rt.ownerId "
			+ "		AND rt.objectableType = 'REQUEST_CANDIDATE_TRANSLATION' "
			+ "WHERE (rt) NOT IN "
			+ "		(SELECT rts.requestTranslation "
			+ "			FROM RequestStatus rts "
			+ "			WHERE (rts.translator.id = :translatorId "
			+ "				AND (rts.status = 'CANCELED' OR rts.status = 'REJECTED')) "
			+ "				OR ((rts.translator.id is null) AND rts.status = 'CANCELED')) "
			+ "		AND (co.name LIKE %:name% OR ca.fullName LIKE %:name%) "
			+ "		AND (rt.translator.id is null) "
			+ "		AND rt.objectableType IN :objectableTypes "
			+ "		AND rt.language.id IN :languageIds "
			+ " 	AND rt.createdAt >= :postTime "
			+ "ORDER BY rt.createdAt DESC")
	public Page<RequestTranslation> findNewRequestByTranslator(Pageable pageable, @Param("name") String name, 
			@Param("objectableTypes") Set<String> objectableTypes, @Param("languageIds") Set<UUID> languageIds, 
			@Param("postTime") Date postTime, @Param("translatorId") UUID transaltorId);
	
}
