package com.japanwork.repository.language_certificate;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.LanguageCertificate;

public interface LanguageCertificateRepository extends JpaRepository<LanguageCertificate, BigInteger>{
	public LanguageCertificate findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<LanguageCertificate> findByCandidateId(UUID id);
}
