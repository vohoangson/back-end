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

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.request.ListDistrictRequest;
import com.japanwork.service.DistrictService;

@Controller
public class DistrictController {
	@Autowired
	private DistrictService districtService;
	
	@GetMapping(UrlConstant.URL_DISTRICTS)
	@ResponseBody
	public ResponseDataAPI index() {
		List<District> list = districtService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICTS)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody DistrictRequest districtRequest) {		
		District district = districtService.create(districtRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, district, null, null);
	}
	
	@GetMapping(UrlConstant.URL_CITIES_DISTRICTS)
	@ResponseBody
	public ResponseDataAPI listDistrictByCity(@PathVariable UUID id) {
		List<District> list = districtService.findAllByCityIdAndIsDelete(id);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
	
	@PostMapping(value = UrlConstant.URL_DISTRICTS_BATCH)
	@ResponseBody
	public ResponseDataAPI createList(@Valid @RequestBody ListDistrictRequest listDistrictRequest) {		
		List<District> list =  districtService.saves(listDistrictRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
}
