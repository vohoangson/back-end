package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import com.japanwork.payload.response.*;
import com.japanwork.support.CommonSupport;
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

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Company;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.CompanyService;
import com.japanwork.service.UserService;

@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

    @Autowired
    private CommonSupport commonSupport;

    @GetMapping(UrlConstant.URL_COMPANIES)
	@ResponseBody
	public BaseDataMetaResponse listCompany(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		Page<Company> pages = companyService.findAllByIsDelete(page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<CompanyResponse> list = new ArrayList<CompanyResponse>();

		if(pages.getContent().size() > 0) {
			for (Company company : pages.getContent()) {
				list.add(companyService.convertCompanyResponse(company));
			}
		}

		return new BaseDataMetaResponse(list, pageInfo);
	}

	@GetMapping(UrlConstant.URL_COMPANY_IDS)
	@ResponseBody
	public BaseDataMetaResponse listCompanyByIds(@RequestParam(defaultValue = "1", name = "page") int page,
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

		return new BaseDataMetaResponse(list, pageInfo);
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

	@PostMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody CompanyRequest companyRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{

		if(companyService.checkCompanyByUser(userService.findById(userPrincipal.getId()))) {
			throw new BadRequestException(MessageConstant.COMPANY_ALREADY, MessageConstant.COMPANY_ALREADY_MSG);
		}

		Company company = companyService.save(companyRequest, userPrincipal);
		return new BaseDataResponse(companyService.convertCompanyResponse(company));
	}

	@GetMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public BaseDataResponse findCompanyByIdAndIsDelete(@PathVariable UUID id){
		Company company = companyService.findByIdAndIsDelete(id);
		return new BaseDataResponse(companyService.convertCompanyResponse(company));
	}

	@GetMapping(UrlConstant.URL_MY_COMPANY)
	@ResponseBody
	public BaseDataResponse myCompany(@CurrentUser UserPrincipal userPrincipal){
		Company company =  companyService.myCompany(userPrincipal);
		return new BaseDataResponse(companyService.convertCompanyResponse(company));
	}

	@PatchMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public BaseDataResponse update(@Valid @RequestBody CompanyRequest companyRequest, @PathVariable UUID id,
			@CurrentUser UserPrincipal userPrincipal){
		Company company = companyService.update(companyRequest, id, userPrincipal);
		return new BaseDataResponse(companyService.convertCompanyResponse(company));
	}

	@DeleteMapping(UrlConstant.URL_COMPANY)
	@ResponseBody
	public BaseDataResponse isDel(@PathVariable UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		companyService.isDel(id, timestamp);
		BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.COMPANY_DELETE_SUCCESS, MessageConstant.COMPANY_DELETE_SUCCESS_MSG);
		return new BaseDataResponse(baseMessageResponse);
	}

	@PatchMapping(UrlConstant.URL_COMPANY_UNDELETE)
	@ResponseBody
	public BaseDataResponse unDel(@PathVariable UUID id) {
		Company company =  companyService.isDel(id, null);
		return new BaseDataResponse(companyService.convertCompanyResponse(company));
	}

	@DeleteMapping(UrlConstant.URL_COMPANY_DEL)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {
		companyService.del(companyService.findById(id));
		BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_SUCCESS);
		return new BaseDataResponse(deleteResponse);
	}
}
