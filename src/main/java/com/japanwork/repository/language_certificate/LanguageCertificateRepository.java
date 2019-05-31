package com.japanwork.repository.language_certificate;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.LanguageCertificate;

public interface LanguageCertificateRepository extends JpaRepository<LanguageCertificate, UUID>{
	public LanguageCertificate findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<LanguageCertificate> findByCandidateId(UUID id);
}
