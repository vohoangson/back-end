package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.BusinessTypeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.BusinessTypeService;

@Controller
public class BusinessTypeController {
	@Autowired
	private BusinessTypeService businessTypeService;
	
	@PostMapping(value = UrlConstant.URL_BUSINESS_CREATE)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody BusinessTypeRequest businessTypeRequest) {		
		return businessTypeService.save(businessTypeRequest);
	}
}
