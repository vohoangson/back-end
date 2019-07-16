package com.japanwork.repository.business_type;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Business;

public interface BusinessTypeRepository extends JpaRepository<Business, UUID>{
	public Business findByIdAndDeleteAt(UUID id, Timestamp deleteAt);
	public List<Business> findAllByDeleteAt(Timestamp deleteAt);
}
