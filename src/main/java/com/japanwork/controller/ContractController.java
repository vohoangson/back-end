package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.service.ContractService;

@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@GetMapping(value = UrlConstant.URL_CONTRACTS)
	@ResponseBody
	public ResponseDataAPI index() {		
		List<Contract> list = contractService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
	
	@PostMapping(value = UrlConstant.URL_CONTRACTS)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody ContractRequest contractRequest) {		
		Contract contract =  contractService.create(contractRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, contract, null, null);
	}
}
