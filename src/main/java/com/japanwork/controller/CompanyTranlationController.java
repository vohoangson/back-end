package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.CompanyTranslationRequest;
import com.japanwork.service.CompanyTranslationService;
import com.japanwork.support.CommonSupport;

@Controller
public class CompanyTranlationController {
	@Autowired
	private CompanyTranslationService companyTranslationService;

	@Autowired
	private CommonSupport commonSupport;

	@PostMapping(UrlConstant.URL_COMPANY_TRANSLATION)
	@ResponseBody
	public ResponseDataAPI create(
            @PathVariable UUID id,
            @Valid @RequestBody CompanyTranslationRequest companyTranslationRequest
    ) throws BadRequestException{
        Company company       = commonSupport.loadCompanyById(id);
        Language language     = commonSupport.loadLanguageById(companyTranslationRequest.getLanguageId());

		CompanyTranslation companyTranslation = companyTranslationService.save(
                company,
                language,
                companyTranslationRequest
        );

		return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
	}

//	@PatchMapping(UrlConstant.URL_COMPANY_TRANSLATION_BY_ID)
//	@ResponseBody
//	public BaseDataResponse update(@Valid @RequestBody CompanyTranslationRequest companyTranslationRequest, @PathVariable UUID id,
//			@CurrentUser UserPrincipal userPrincipal){
//		CompanyTranslation companyTranslation = companyTranslationService.update(companyTranslationRequest, id, userPrincipal);
//		return new BaseDataResponse(companyTranslationService.convertCompanyResponse(companyTranslation));
//	}
}
