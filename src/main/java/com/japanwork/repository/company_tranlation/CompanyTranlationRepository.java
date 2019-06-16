package com.japanwork.repository.company_tranlation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.CompanyTranslation;

public interface CompanyTranlationRepository extends JpaRepository<CompanyTranslation, UUID>{
	public CompanyTranslation findByIdAndIsDelete(UUID id, boolean isDelete);
}
