package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Company;
import com.japanwork.model.RequestDetail;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.request.RequestDetailRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.RequestDetailService;

@Controller
public class RequestDetailController {
	
	@Autowired
	private RequestDetailService requestDetailService;
	
	@PostMapping(UrlConstant.URL_REQUEST_TRANSLATION)
	@ResponseBody
	public BaseDataResponse createRequest(@Valid @RequestBody RequestDetailRequest requestDetailRequest, 
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		
		RequestDetail requestDetail = requestDetailService.createRequestDetail(requestDetailRequest, userPrincipal);
		return new BaseDataResponse(requestDetail);
	}
}
