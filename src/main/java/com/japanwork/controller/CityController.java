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

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.request.ListCityRequest;
import com.japanwork.service.CityService;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;


	@GetMapping(UrlConstant.URL_CITIES)
	@ResponseBody
	public ResponseDataAPI index() {
		List<City> list = cityService.index();
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                list,
                ""
        );
	}

	@PostMapping(value = UrlConstant.URL_CITIES)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody CityRequest cityRequest) {
		City city = cityService.create(cityRequest);
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                city,
                ""
        );
	}

	@GetMapping(UrlConstant.URL_COUNTRIES_CITIES)
	@ResponseBody
	public ResponseDataAPI listCityByCountry(@PathVariable String code) {
		List<City> list = cityService.listCityByCountry(code);
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                list,
                ""
        );
	}

	@PostMapping(value = UrlConstant.URL_CITIES_BATCH)
	@ResponseBody
	public ResponseDataAPI createList(@Valid @RequestBody ListCityRequest listCityRequest) {
		List<City> list =  cityService.saves(listCityRequest);
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                list,
                ""
        );
	}
}
