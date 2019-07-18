package com.japanwork.repository.company_tranlation;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.CompanyTranslation;

public interface CompanyTranlationRepository extends JpaRepository<CompanyTranslation, UUID>{
	public CompanyTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
