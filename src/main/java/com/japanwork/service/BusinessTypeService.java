package com.japanwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.Business;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.repository.business_type.BusinessTypeRepository;

@Service
public class BusinessTypeService {
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	public Business create(BusinessRequest businessTypeRequest) {		
		Business businessType = new Business();
		businessType.setJa(businessTypeRequest.getJa());
		businessType.setVi(businessTypeRequest.getVi());
		businessType.setDesc(businessTypeRequest.getDesc());
		businessType.setCreatedAt(CommonFunction.dateTimeNow());
		businessType.setUpdatedAt(null);
		businessType.setDeletedAt(null);
		
		Business result = businessTypeRepository.save(businessType);	
		return result;
	}
	
	public List<Business> index() {
		List<Business> list = businessTypeRepository.findAllByDeletedAt(null);
		return list;
	} 
}
