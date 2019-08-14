package com.japanwork.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.ContractService;

@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@GetMapping(value = UrlConstant.URL_CONTRACTS)
	@ResponseBody
	public BaseDataResponse listContract() {		
		List<Contract> list = contractService.findAllByIsDelete();
		return new BaseDataResponse(list);
	}
	
	@GetMapping(value = UrlConstant.URL_CONTRACT)
	@ResponseBody
	public BaseDataResponse show(@PathVariable UUID id) {		
		Contract contract = contractService.findByIdAndIsDelete(id);
		return new BaseDataResponse(contract);
	}
	
	@PostMapping(value = UrlConstant.URL_CONTRACTS)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody ContractRequest contractRequest) {		
		Contract contract =  contractService.save(contractRequest);
		return new BaseDataResponse(contract);
	}
}
