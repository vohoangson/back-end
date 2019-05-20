package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CityService;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;
	
	@PostMapping(value = UrlConstant.URL_CITY)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CityRequest cityRequest) {		
		return cityService.save(cityRequest);
	}
}
