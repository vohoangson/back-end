package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Business;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BusinessResponse;
import com.japanwork.repository.businesstype.BusinessTypeRepository;

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
		businessType.setIsDelete(false);
		
		Business result = businessTypeRepository.save(businessType);
		BusinessResponse businessResponse = this.setBusinessResponse(result);
		BaseDataResponse response = new BaseDataResponse(businessResponse);	
		
		return response;
	}
	
	public Business findByIdAndIsDelete(UUID id) {
		return businessTypeRepository.findByIdAndIsDelete(id, false);
	}
	
	public BusinessResponse convertBusiness(UUID id) throws ResourceNotFoundException{
		Business business = businessTypeRepository.findByIdAndIsDelete(id, false);
		if(business == null) {
			throw new ResourceNotFoundException("Business not found for this id :: " + id);
		}
		BusinessResponse businessResponse = this.setBusinessResponse(business);
		
		return businessResponse;
	}
	
	private BusinessResponse setBusinessResponse(Business business) {
		BusinessResponse businessResponse = new BusinessResponse();
		businessResponse.setNameVi(business.getNameVi());
		businessResponse.setNameJa(business.getNameJa());
		businessResponse.setDescription(business.getDescription());
		return businessResponse;
	}
}
