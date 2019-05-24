package com.japanwork.repository.business_type;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Business;

public interface BusinessTypeRepository extends JpaRepository<Business, UUID>{
	public Business findByIdAndIsDelete(UUID id, boolean isDelete);
}
