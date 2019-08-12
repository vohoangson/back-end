package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.request.ListCityRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CityService;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;
	
	
	@GetMapping(UrlConstant.URL_CITIES)
	@ResponseBody
	public BaseDataResponse listCity() {
		List<City> list = cityService.findAllByIsDelete();
		return new BaseDataResponse(list);
	}
	
	@GetMapping(UrlConstant.URL_COUNTRIES_CITIES)
	@ResponseBody
	public BaseDataResponse listCityByCountry(@PathVariable String code) {
		List<City> list = cityService.listCityByCountry(code);
		return new BaseDataResponse(list);
	}
	
	@PostMapping(value = UrlConstant.URL_CITIES)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CityRequest cityRequest) {		
		City city = cityService.save(cityRequest);
		return new BaseDataResponse(city);
	}
	
	@PostMapping(value = UrlConstant.URL_CITIES_BATCH)
	@ResponseBody
	public BaseDataResponse createList(@Valid @RequestBody ListCityRequest listCityRequest) {		
		List<City> list =  cityService.saves(listCityRequest);
		return new BaseDataResponse(list);
	}
}
