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
import com.japanwork.model.Company;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.service.CompanyService;

@Controller
@RequestMapping(UrlConstant.URL_COMPANY)
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@PostMapping(UrlConstant.URL_COMPANY_CREATE)
	@ResponseBody
	public Company create(@Valid @RequestBody CompanyRequest companyRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		company.setUserId(companyRequest.getUserId());
		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBussinessTypeId(companyRequest.getBusinessId());
		company.setCityId(companyRequest.getCityId());
		company.setDistrictId(companyRequest.getDistrictId());
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setCreateDate(timestamp);
		company.setUpdateDate(timestamp);
		company.setDelete(false);
		
		Company result = companyService.save(company);
		return result;
	}
}
