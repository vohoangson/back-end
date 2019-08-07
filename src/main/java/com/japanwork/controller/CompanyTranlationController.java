package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.payload.request.CompanyTranslationRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseSuccessResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.CompanyTranslationService;
import com.japanwork.service.UserService;
import com.japanwork.support.CommonSupport;

@Controller
public class CompanyTranlationController {
	@Autowired
	private CompanyTranslationService companyTranslationService;

	@Autowired
	private CommonSupport commonSupport;

	@Autowired
	private UserService userService;

	@PostMapping(UrlConstant.URL_COMPANY_TRANSLATION)
	@ResponseBody
	public BaseSuccessResponse create(@Valid @RequestBody CompanyTranslationRequest companyTranslationRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{

	    commonSupport.loadCompany(companyTranslationRequest.getCompanyId());
        commonSupport.loadLanguage(companyTranslationRequest.getLanguageId());

		CompanyTranslation companyTranslation = companyTranslationService.save(companyTranslationRequest, userPrincipal);
		return new BaseSuccessResponse(
		        "success",
		        null,
                null
        );
	}

	@PatchMapping(UrlConstant.URL_COMPANY_TRANSLATION_BY_ID)
	@ResponseBody
	public BaseDataResponse update(@Valid @RequestBody CompanyTranslationRequest companyTranslationRequest, @PathVariable UUID id,
			@CurrentUser UserPrincipal userPrincipal){
		CompanyTranslation companyTranslation = companyTranslationService.update(companyTranslationRequest, id, userPrincipal);
		return new BaseDataResponse(companyTranslationService.convertCompanyResponse(companyTranslation));
	}
}
