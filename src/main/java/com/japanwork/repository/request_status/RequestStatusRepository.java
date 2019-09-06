package com.japanwork.repository.request_status;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.japanwork.model.RequestStatus;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, UUID>, QuerydslPredicateExecutor<RequestStatus>{
	public List<RequestStatus> findByRequestTranslationIdOrderByCreatedAtDesc(UUID requestTranslationId);

}
