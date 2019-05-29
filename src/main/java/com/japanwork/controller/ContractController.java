package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.ContractService;

@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@PostMapping(value = UrlConstant.URL_CONTRACT)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody ContractRequest contractRequest) {		
		return contractService.save(contractRequest);
	}
}