package com.japanwork.repository.business_type;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Business;

public interface BusinessTypeRepository extends JpaRepository<Business, BigInteger>{
	public Business findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
	public List<Business> findAllByDeletedAt(Timestamp deletedAt);
}
