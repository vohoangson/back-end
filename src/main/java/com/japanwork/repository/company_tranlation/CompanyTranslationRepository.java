package com.japanwork.repository.company_tranlation;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Language;

public interface CompanyTranslationRepository extends JpaRepository<CompanyTranslation, UUID>{
	public CompanyTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<CompanyTranslation> findByCompanyAndDeletedAt(Company company, Timestamp deletedAt);
	public Page<CompanyTranslation> findAllByLanguageAndDeletedAt(Pageable page, Language language, Timestamp deletedAt);
	public CompanyTranslation findByCompanyAndLanguageAndDeletedAt(Company company, Language language, Timestamp deletedAt);
}
