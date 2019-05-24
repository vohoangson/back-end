package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Business;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.business_type.BusinessTypeRepository;

@Service
public class BusinessService {
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	public BaseDataResponse save(BusinessRequest businessTypeRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Business businessType = new Business();
		businessType.setNameJa(businessTypeRequest.getNameJa());
		businessType.setNameVi(businessTypeRequest.getNameVi());
		businessType.setDescription(businessTypeRequest.getDescription());
		businessType.setCreateDate(timestamp);
		businessType.setUpdateDate(timestamp);
		businessType.setDelete(false);
		
		Business result = businessTypeRepository.save(businessType);
		BaseDataResponse response = new BaseDataResponse(result);	
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		Business business = businessTypeRepository.findByIdAndIsDelete(id, false);
		if(business == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(business);	
		return response;
	}
}
