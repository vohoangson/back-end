package com.japanwork.repository.request_status;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.japanwork.model.RequestStatus;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, UUID>{
	public List<RequestStatus> findByRequestTranslationIdOrderByCreatedAtDesc(UUID requestTranslationId);
	
	@Query("SELECT rts FROM RequestStatus rts "
			+ "INNER JOIN rts.requestTranslation rt "
			+ "LEFT JOIN Company co "
			+ "		ON co.id = rt.ownerId "
			+ "		AND rt.objectableType != 'REQUEST_CANDIDATE_TRANSLATION' "
			+ "LEFT JOIN Candidate ca "
			+ "		ON ca.id = rt.ownerId "
			+ "		AND rt.objectableType = 'REQUEST_CANDIDATE_TRANSLATION' "
			+ "WHERE (rts.requestTranslation, rts.translator, rts.createdAt ) IN "
			+ "		(SELECT rts1.requestTranslation, rts1.translator, max(rts1.createdAt) AS createdAt "
			+ "			FROM RequestStatus rts1 "
			+ "			WHERE rts1.translator.id = :translatorId "
			+ "			GROUP BY ( rts1.requestTranslation, rts1.translator )) "
			+ "		AND (co.name LIKE %:name% OR ca.fullName LIKE %:name%) "
			+ "		AND rts.requestTranslation.objectableType IN :objectableTypes "
			+ "		AND rts.requestTranslation.language.id IN :languageIds "
			+ " 	AND rts.requestTranslation.createdAt >= :postTime "
			+ "ORDER BY rts.createdAt DESC")
	public Page<RequestStatus> findAllByTranslator(Pageable pageable, @Param("name") String name, 
			@Param("objectableTypes") Set<String> objectableTypes, @Param("languageIds") Set<UUID> languageIds, 
			@Param("postTime") Date postTime, @Param("translatorId") UUID transaltorId);
}
