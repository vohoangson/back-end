package com.japanwork.controller;

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
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CompanyService;

@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping(UrlConstant.URL_COMPANY_LIST)
	@ResponseBody
	public BaseDataResponse listCompany() {		
		return companyService.findAll();
	}
	
	@PostMapping(UrlConstant.URL_COMPANY_CREATE)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CompanyRequest companyRequest) {		
		return companyService.save(companyRequest);
	}
	
	@GetMapping(UrlConstant.URL_COMPANY_FIND_BY_ID)
	@ResponseBody
	public BaseDataResponse findCompanyById(@PathVariable UUID id) {		
		return companyService.findById(id);
	}
	
	@PostMapping(UrlConstant.URL_COMPANY_UPDATE)
	@ResponseBody
	public BaseDataResponse update(@Valid @RequestBody CompanyRequest companyRequest, @PathVariable UUID id) {		
		return companyService.update(companyRequest, id);
	}
	
	@GetMapping(UrlConstant.URL_COMPANY_DELETE)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {		
		return companyService.del(id);
	}
}
