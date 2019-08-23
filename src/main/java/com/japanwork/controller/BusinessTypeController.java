package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Business;
import com.japanwork.payload.request.BusinessRequest;
import com.japanwork.service.BusinessTypeService;

@Controller
public class BusinessTypeController {
	@Autowired
	private BusinessTypeService businessTypeService;


	@GetMapping(UrlConstant.URL_BUSINESSES)
	@ResponseBody
	public ResponseDataAPI index() {
		List<Business> list = businessTypeService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null);
	}

	@PostMapping(value = UrlConstant.URL_BUSINESSES)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody BusinessRequest businessTypeRequest) {
		Business business = businessTypeService.create(businessTypeRequest);
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                business,
                ""
        );
	}
}
