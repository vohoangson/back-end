package com.japanwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Company;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.CompanyService;
import com.japanwork.support.CommonSupport;

@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;

    @Autowired
    private CommonSupport commonSupport;

    @GetMapping(UrlConstant.URL_COMPANIES)
	@ResponseBody
	public ResponseDataAPI index(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		Page<Company> pages = companyService.index(page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<CompanyResponse> list = new ArrayList<CompanyResponse>();

		if(pages.getContent().size() > 0) {
			for (Company company : pages.getContent()) {
				list.add(companyService.convertCompanyResponse(company));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	@GetMapping(UrlConstant.URL_COMPANY_IDS)
	@ResponseBody
	public ResponseDataAPI listCompanyByIds(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(name = "ids") Set<UUID> ids) {

		Page<Company> pages = companyService.companiesByIds(ids, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<CompanyResponse> list = new ArrayList<CompanyResponse>();

		if(pages.getContent().size() > 0) {
			for (Company company : pages.getContent()) {
				list.add(companyService.convertCompanyResponse(company));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

//	@GetMapping(UrlConstant.URL_COMPANY)
//    @ResponseBody
//    public BaseSuccessResponse show(
//            @PathVariable UUID id,
//            @RequestParam UUID language_id
//    ) throws BadRequestException {
//        commonSupport.loadLanguage(language_id);
//
//        Company company = companyService.show(id, language_id);
//        return new BaseSuccessResponse(
//                "success",
//                companyService.convertCompanyResponse(company),
//                null
//        );
//    }

	@PostMapping(UrlConstant.URL_COMPANIES)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody CompanyRequest companyRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		Company company = companyService.create(companyRequest, userPrincipal);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
                companyService.convertCompanyResponse(company),
				""
        );
	}

	@GetMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public ResponseDataAPI show(@PathVariable UUID id){
		Company company = commonSupport.loadCompanyById(id);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				companyService.convertCompanyResponse(company),
				""
        );
	}

	@GetMapping(UrlConstant.URL_MY_COMPANY)
	@ResponseBody
	public ResponseDataAPI myCompany(@CurrentUser UserPrincipal userPrincipal){
		Company company =  commonSupport.loadCompanyByUser(userPrincipal.getId());
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				companyService.convertCompanyResponse(company),
				""
        );
	}

	@PatchMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public ResponseDataAPI update(@Valid @RequestBody CompanyRequest companyRequest, @PathVariable UUID id,
			@CurrentUser UserPrincipal userPrincipal){
		Company company = commonSupport.loadCompanyById(id);
		company = companyService.update(companyRequest, company, userPrincipal);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				companyService.convertCompanyResponse(company),
				""
        );
	}

	@DeleteMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public ResponseDataAPI destroy(@PathVariable UUID id) {
		companyService.isDel(id, CommonFunction.getCurrentDateTime());
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				"",
				""
        );
	}

	@PatchMapping(UrlConstant.URL_COMPANY_UNDELETE)
	@ResponseBody
	public ResponseDataAPI unDel(@PathVariable UUID id) {
		Company company =  companyService.isDel(id, null);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				companyService.convertCompanyResponse(company),
				""
        );
	}
}
