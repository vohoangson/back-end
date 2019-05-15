package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.BusinessType;
import com.japanwork.repository.businesstype.BusinessTypeRepository;

@Service
public class BusinessTypeService {
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	public BusinessType save(BusinessType businessType) {
		return businessTypeRepository.save(businessType);
	}
	
	public BusinessType findById(UUID id) {
		return businessTypeRepository.findById(id).get();
	}
}
