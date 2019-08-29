package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.District;
import com.japanwork.model.Language;
import com.japanwork.model.User;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.company.CreateCompanyService;
import com.japanwork.service.company.IndexCompanyService;
import com.japanwork.service.company.UpdateCompanyService;
import com.japanwork.support.CommonSupport;

@Controller
public class CompanyController {
    @Autowired
    private CommonSupport commonSupport;

    @Autowired
	private IndexCompanyService indexCompanyService;
    
    @Autowired
	private CreateCompanyService createCompanyService;
    
    @Autowired
    private UpdateCompanyService updateCompanyService;
    
    @GetMapping(UrlConstant.URL_COMPANIES)
	@ResponseBody
	public ResponseDataAPI index(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(name = "language") String languageCode) {
    	Language language = commonSupport.loadLanguage(languageCode);
		return indexCompanyService.perform(page, paging, language);
	}

    @PostMapping(UrlConstant.URL_COMPANIES)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody CompanyRequest companyRequest, @CurrentUser UserPrincipal userPrincipal) {
    	User user = commonSupport.loadUserById(userPrincipal.getId());
    	City city = commonSupport.loadCity(companyRequest.getCityId());
    	District district = commonSupport.loadDistrict(companyRequest.getDistrictId());
    	
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				createCompanyService.perform(companyRequest, user, city, district),
				""
        );
	}
    
	@GetMapping(UrlConstant.URL_COMPANY)
    @ResponseBody
    public ResponseDataAPI show(@PathVariable UUID id, @RequestParam(name = "language") String languageCode) {
        Language language = commonSupport.loadLanguage(languageCode);
        Company company = commonSupport.loadCompanyById(id);
        CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);
        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                new CompanyResponse().companyFullSerializer(company, companyTranslation),
                ""
        );
    }

	@PatchMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public ResponseDataAPI update(
	        @Valid @RequestBody CompanyRequest companyRequest,
            @PathVariable UUID id,
			@CurrentUser UserPrincipal userPrincipal) {
		Company company = commonSupport.loadCompanyById(id);
		if(!company.getUser().getId().equals(userPrincipal.getId())) {
			throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
		}
		
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				updateCompanyService.perform(companyRequest, company),
				""
        );
	}
	
	@GetMapping(UrlConstant.URL_MY_COMPANY)
	@ResponseBody
	public ResponseDataAPI myCompany(@CurrentUser UserPrincipal userPrincipal) {
		Company company =  commonSupport.loadCompanyByUser(userPrincipal.getId());
		Language language = commonSupport.loadUserById(userPrincipal.getId()).getCountry().getLanguage();
		CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				new CompanyResponse().companyFullSerializer(company, companyTranslation),
				""
        );
	}
}
