package com.japanwork.repository.request_translation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.HistoryStatus;

public interface HistoryStatusRepository extends JpaRepository<HistoryStatus, UUID>{
	public List<HistoryStatus> findByObjecttableIdOrderByCreatedAtDesc(UUID requestTranslationId);
}
