package com.japanwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Country;
import com.japanwork.service.CountryService;

@Controller
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@GetMapping(UrlConstant.URL_COUNTRIES)
	@ResponseBody
	public ResponseDataAPI index() {
		List<Country> list = countryService.index();
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                list,
                ""
        );
	}
}	
