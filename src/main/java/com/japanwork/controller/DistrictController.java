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
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.request.ListDistrictRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.DistrictService;

@Controller
public class DistrictController {
	@Autowired
	private DistrictService districtService;
	
	@GetMapping(UrlConstant.URL_DISTRICTS)
	@ResponseBody
	public BaseDataResponse listDistrict() {
		List<District> list = districtService.findAllByIsDelete();
		return new BaseDataResponse(list);
	}
	
	@GetMapping(UrlConstant.URL_CITIES_DISTRICTS)
	@ResponseBody
	public BaseDataResponse listDistrictByCity(@PathVariable UUID id) {
		List<District> list = districtService.findAllByCityIdAndIsDelete(id);
		return new BaseDataResponse(list);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICTS)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody DistrictRequest districtRequest) {		
		District district = districtService.save(districtRequest);
		return new BaseDataResponse(district);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICTS_BATCH)
	@ResponseBody
	public BaseDataResponse createList(@Valid @RequestBody ListDistrictRequest listDistrictRequest) {		
		List<District> list =  districtService.saves(listDistrictRequest);
		return new BaseDataResponse(list);
	}
}
