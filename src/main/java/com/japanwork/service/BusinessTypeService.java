package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.BusinessType;
import com.japanwork.payload.request.BusinessTypeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.businesstype.BusinessTypeRepository;

@Service
public class BusinessTypeService {
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	public BaseDataResponse save(BusinessTypeRequest businessTypeRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		BusinessType businessType = new BusinessType();
		businessType.setNameJa(businessTypeRequest.getNameJa());
		businessType.setNameVi(businessTypeRequest.getNameVi());
		businessType.setDescription(businessTypeRequest.getDescription());
		businessType.setCreateDate(timestamp);
		businessType.setUpdateDate(timestamp);
		businessType.setIsDelete(false);
		
		BusinessType result = businessTypeRepository.save(businessType);
		BaseDataResponse response = new BaseDataResponse(result);	
		
		return response;
	}
	
	public BusinessType findById(UUID id) {
		return businessTypeRepository.findByIdAndIsDelete(id, false);
	}
}
