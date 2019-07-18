package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Business;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.repository.business_type.BusinessTypeRepository;

@Service
public class BusinessService {
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	public Business save(BusinessRequest businessTypeRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Business businessType = new Business();
		businessType.setJa(businessTypeRequest.getJa());
		businessType.setVi(businessTypeRequest.getVi());
		businessType.setDesc(businessTypeRequest.getDesc());
		businessType.setCreatedAt(timestamp);
		businessType.setUpdatedAt(timestamp);
		businessType.setDeletedAt(null);
		
		Business result = businessTypeRepository.save(businessType);	
		return result;
	}
	
	public Business findByIdAndIsDelete(UUID id) {
		Business business = businessTypeRepository.findByIdAndDeletedAt(id, null);
		if(business == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return business;
	}
	
	public List<Business> findAllByIsDelete() {
		List<Business> list = businessTypeRepository.findAllByDeletedAt(null);
		return list;
	} 
}
