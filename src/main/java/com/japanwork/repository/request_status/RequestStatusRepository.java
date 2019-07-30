package com.japanwork.repository.request_status;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.RequestStatus;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, UUID>{
	public List<RequestStatus> findByRequestTranslationIdOrderByCreatedAtDesc(UUID requestTranslationId);
}
