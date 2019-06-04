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
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.request.ListDistrictRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.DistrictService;

@Controller
public class DistrictController {
	@Autowired
	private DistrictService districtService;
	
	@GetMapping(UrlConstant.URL_DISTRICT)
	@ResponseBody
	public BaseDataResponse listDistrict() {
		return districtService.findAllByIsDelete();
	}
	
	@GetMapping(UrlConstant.URL_DISTRICTS_ID)
	@ResponseBody
	public BaseDataResponse listDistrictByCity(@PathVariable UUID id) {
		return districtService.findAllByCityIdAndIsDelete(id);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICT)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody DistrictRequest districtRequest) {		
		return districtService.save(districtRequest);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICTS)
	@ResponseBody
	public BaseDataResponse createList(@Valid @RequestBody ListDistrictRequest listDistrictRequest) {		
		return districtService.saves(listDistrictRequest);
	}
}
