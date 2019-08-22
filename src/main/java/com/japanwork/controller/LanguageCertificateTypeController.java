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
import com.japanwork.model.LanguageCertificateType;
import com.japanwork.payload.request.LanguageCertificateTypeRequest;
import com.japanwork.service.LanguageCertificateTypeService;

@Controller
public class LanguageCertificateTypeController {
	
	@Autowired
	private LanguageCertificateTypeService languageCertificateTypeService;
	
	@GetMapping(UrlConstant.URL_LANGUAGUE_TYPES)
	@ResponseBody
	public ResponseDataAPI index() {
		List<LanguageCertificateType> list = languageCertificateTypeService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
	
	@PostMapping(value = UrlConstant.URL_LANGUAGUE_TYPES)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody LanguageCertificateTypeRequest languageCertificateTypeRequest) {		
		LanguageCertificateType obj = languageCertificateTypeService.create(languageCertificateTypeRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, obj, null, null);
	}
}
