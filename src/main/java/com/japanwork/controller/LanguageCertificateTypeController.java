package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.LanguageCertificateTypeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.LanguageCertificateTypeService;

@Controller
public class LanguageCertificateTypeController {
	
	@Autowired
	private LanguageCertificateTypeService languageCertificateTypeService;
	
	@GetMapping(UrlConstant.URL_LANGUAGUE_TYPE)
	@ResponseBody
	public BaseDataResponse listLanguageCertificateType() {
		return languageCertificateTypeService.findAllByIsDelete();
	}
	
	@PostMapping(value = UrlConstant.URL_LANGUAGUE_TYPE)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody LanguageCertificateTypeRequest languageCertificateTypeRequest) {		
		return languageCertificateTypeService.save(languageCertificateTypeRequest);
	}
}
