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
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.request.ListCityRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CityService;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;
	
	
	@GetMapping(UrlConstant.URL_CITY)
	@ResponseBody
	public BaseDataResponse listCity() {
		return cityService.findAllByIsDelete();
	}
	
	@GetMapping(UrlConstant.URL_CITIES_ID)
	@ResponseBody
	public BaseDataResponse listCityByCountry(@PathVariable UUID id) {
		return cityService.listCityByCountry(id);
	}
	
	@PostMapping(value = UrlConstant.URL_CITY)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CityRequest cityRequest) {		
		return cityService.save(cityRequest);
	}
	
	@PostMapping(value = UrlConstant.URL_CITIES)
	@ResponseBody
	public BaseDataResponse createList(@Valid @RequestBody ListCityRequest listCityRequest) {		
		return cityService.saves(listCityRequest);
	}
}
