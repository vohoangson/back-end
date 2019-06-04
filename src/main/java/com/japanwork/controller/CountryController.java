package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.CountryRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CountryService;

@Controller
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	
	@GetMapping(UrlConstant.URL_COUNTRY)
	@ResponseBody
	public BaseDataResponse listCity() {
		return countryService.findAllByIsDelete();
	}
	
	@PostMapping(value = UrlConstant.URL_COUNTRY)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CountryRequest countryRequest) {		
		return countryService.save(countryRequest);
	}
}
