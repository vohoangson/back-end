package com.japanwork.repository.businesstype;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.BusinessType;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, UUID>{

}
