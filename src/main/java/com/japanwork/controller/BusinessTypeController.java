package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.BusinessType;
import com.japanwork.payload.request.BusinessTypeRequest;
import com.japanwork.service.BusinessTypeService;

@Controller
@RequestMapping(UrlConstant.URL_BUSINESS_TYPE)
public class BusinessTypeController {
	@Autowired
	private BusinessTypeService businessTypeService;
	
	@PostMapping(value = UrlConstant.URL_BUSINESS_TYPE_CREATE)
	@ResponseBody
	public BusinessType create(@Valid @RequestBody BusinessTypeRequest businessTypeRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		BusinessType businessType = new BusinessType();
		businessType.setNameJa(businessTypeRequest.getNameJa());
		businessType.setNameVi(businessTypeRequest.getNameVi());
		businessType.setDescription(businessTypeRequest.getDescription());
		businessType.setCreateDate(timestamp);
		businessType.setUpdateDate(timestamp);
		businessType.setIsDelete(false);
		
		BusinessType result = businessTypeService.save(businessType);
		
		return result;
	}
}
