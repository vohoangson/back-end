package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.LanguageCertificateType;
import com.japanwork.payload.request.LanguageCertificateTypeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.LanguageCertificateTypeService;

@Controller
public class LanguageCertificateTypeController {
	
	@Autowired
	private LanguageCertificateTypeService languageCertificateTypeService;
	
	@GetMapping(UrlConstant.URL_LANGUAGUE_TYPES)
	@ResponseBody
	public BaseDataResponse listLanguageCertificateType() {
		List<LanguageCertificateType> list = languageCertificateTypeService.findAllByIsDelete();
		return new BaseDataResponse(list);
	}
	
	@PostMapping(value = UrlConstant.URL_LANGUAGUE_TYPES)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody LanguageCertificateTypeRequest languageCertificateTypeRequest) {		
		LanguageCertificateType obj = languageCertificateTypeService.save(languageCertificateTypeRequest);
		return new BaseDataResponse(obj);
	}
}
