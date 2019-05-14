package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.service.DistrictService;

@Controller
@RequestMapping(UrlConstant.URL_DISTRICT)
public class DistrictController {
	@Autowired
	private DistrictService districtService;
	
	@PostMapping(value = UrlConstant.URL_DISTRICT_CREATE)
	@ResponseBody
	public District create(@Valid @RequestBody DistrictRequest districtRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		District district = new District();
		district.setNameJa(districtRequest.getNameJa());
		district.setNameVi(districtRequest.getNameVi());
		district.setCountryCode(districtRequest.getCountryCode());
		district.setDescription(districtRequest.getDescription());
		district.setCreateDate(timestamp);
		district.setUpdateDate(timestamp);
		district.setIsDelete(false);
		
		District result = districtService.save(district);
		
		return result;
	}
}
