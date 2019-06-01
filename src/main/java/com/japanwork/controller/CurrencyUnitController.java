package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.CurrencyUnitRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.CurrencyUnitService;

@Controller
public class CurrencyUnitController {
	@Autowired
	private CurrencyUnitService currencyUnitService;
	
	@GetMapping(UrlConstant.URL_CURRENCYUNIT)
	@ResponseBody
	public BaseDataResponse listCurrencyUnit() {
		return currencyUnitService.findAllByIsDelete();
	}
	
	@PostMapping(value = UrlConstant.URL_CURRENCYUNIT)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CurrencyUnitRequest currencyUnitRequest) {		
		return currencyUnitService.save(currencyUnitRequest);
	}
}
