package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.District;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.request.CompanyTranslationRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company_tranlation.CompanyTranlationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyTranslationService {
	@Autowired
	private CompanyTranlationRepository companyTranlationRepository;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TranslatorService translatorService;
	
	public BaseDataResponse save(CompanyTranslationRequest companyRequest, UserPrincipal userPrincipal) throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			CompanyTranslation company = new CompanyTranslation();
			company.setCompany(new Company(companyRequest.getCompanyId()));
			company.setTranslator(translatorService.findTranslatorByUser(userService.findById(userPrincipal.getId())));
			company.setName(companyRequest.getName());
			company.setAddress(companyRequest.getAddress());
			company.setIntroduction(companyRequest.getIntroduction());
			company.setStatus(1);
			company.setCreateDate(timestamp);
			company.setUpdateDate(timestamp);
			company.setDelete(false);
			
			CompanyTranslation result = companyTranlationRepository.save(company);
			BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(result));		
			return response;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CREATE_COMPANY_TRANSLATE_FAIL);
		}
	}
	
	public BaseDataResponse update(CompanyTranslationRequest companyTranslationRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			CompanyTranslation company = new CompanyTranslation();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_TRANSLATOR")) {
				company = companyTranlationRepository.findByIdAndIsDelete(id, false);
				if(company == null) {
					throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
				}
				if(!(company.getTranslator()).getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			} else {
				company = companyTranlationRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}
	
			company.setName(companyTranslationRequest.getName());
			company.setAddress(companyTranslationRequest.getAddress());
			company.setIntroduction(companyTranslationRequest.getIntroduction());
			company.setStatus(1);
			company.setUpdateDate(timestamp);
			
			CompanyTranslation result = companyTranlationRepository.save(company);
			BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(result));			
			return response;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (ForbiddenException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.UPDATE_COMPANY_TRANSLATE_FAIL);
		}
	}
	
	private CompanyResponse convertCompanyResponse(CompanyTranslation companyTranslation) {
		Company company = companyService.findById(companyTranslation.getCompany().getId());
		CompanyResponse companyResponse = new CompanyResponse(
				company.getId(),
				companyTranslation.getName(), 
				company.getScale(), 
				Business.listBusinessID(company.getBusinesses()),
				company.getCity().getId(), 
				company.getDistrict().getId(), 
				companyTranslation.getAddress(), 
				company.getLogoUrl(), 
				company.getCoverImageUrl(),
				companyTranslation.getIntroduction());
		return companyResponse;
	}
}
