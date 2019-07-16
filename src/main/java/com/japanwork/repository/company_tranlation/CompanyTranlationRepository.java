package com.japanwork.repository.company_tranlation;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.CompanyTranslation;

public interface CompanyTranlationRepository extends JpaRepository<CompanyTranslation, BigInteger>{
	public CompanyTranslation findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
}
