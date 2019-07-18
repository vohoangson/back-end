package com.japanwork.repository.language_certificate_type;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.LanguageCertificateType;

public interface LanguageCertificateTypeRepository extends JpaRepository<LanguageCertificateType, UUID>{
	public LanguageCertificateType findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<LanguageCertificateType> findAllByDeletedAt(Timestamp deletedAt);
}
