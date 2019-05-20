package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.BusinessService;

@Controller
public class BusinessTypeController {
	@Autowired
	private BusinessService businessTypeService;
	
	@PostMapping(value = UrlConstant.URL_BUSINESS)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody BusinessRequest businessTypeRequest) {		
		return businessTypeService.save(businessTypeRequest);
	}
}
