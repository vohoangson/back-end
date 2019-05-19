package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Company;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BusinessTypeService businessTypeService;
	@Autowired
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	
	public BaseDataResponse save(CompanyRequest companyRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		company.setUserId(userPrincipal.getId());
		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBussinessTypeId(companyRequest.getBusinesses().getId());
		company.setCityId(companyRequest.getCity().getId());
		company.setDistrictId(companyRequest.getDistrict().getId());
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setCreateDate(timestamp);
		company.setUpdateDate(timestamp);
		company.setDelete(false);
		
		Company result = companyRepository.save(company);

		CompanyResponse comResponse = this.setCompanyResponse(result);
		
		BaseDataResponse response = new BaseDataResponse(comResponse);		
		return response;
	}
	
	public BaseDataResponse update(CompanyRequest companyRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
			company = companyRepository.findByIdAndIsDelete(id, false);
			if(company == null) {
				throw new ResourceNotFoundException("Company not found for this id :: " + id);
			}
		} else {
			company = companyRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));
		}

		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBussinessTypeId(companyRequest.getBusinesses().getId());
		company.setCityId(companyRequest.getCity().getId());
		company.setDistrictId(companyRequest.getDistrict().getId());
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setUpdateDate(companyRequest.getCreateDate());
		company.setUpdateDate(timestamp);
		company.setDelete(false);
		
		Company result = companyRepository.save(company);

		CompanyResponse comResponse = this.setCompanyResponse(result);
		
		BaseDataResponse response = new BaseDataResponse(comResponse);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));
		company.setDelete(true);
		Company result = companyRepository.save(company);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse unDel(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));
		company.setDelete(false);
		Company result = companyRepository.save(company);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndIsDelete(id, false);
		if(company == null) {
			throw new ResourceNotFoundException("Company not found for this id :: " + id);
		}
		CompanyResponse comResponse = this.setCompanyResponse(company);
		
		BaseDataResponse response = new BaseDataResponse(comResponse);
		
		return response;
	}
	
	public BaseDataResponse findAllByIsDelete() {
		List<Company> listCompany = companyRepository.findAllByIsDelete(false);
		
		List<CompanyResponse> ListCompanyResponses = new ArrayList<CompanyResponse>();
		for (Company item : listCompany) {
			CompanyResponse companyResponses = this.setCompanyResponse(item);
			ListCompanyResponses.add(companyResponses);
		}
		
		
		BaseDataResponse response = new BaseDataResponse(ListCompanyResponses);
		
		return response;
	}
	
	private CompanyResponse setCompanyResponse(Company company) {
		CompanyResponse comResponse = new CompanyResponse();
		
		comResponse.setId(company.getId());
		comResponse.setUser(userService.findById(company.getUserId()));
		comResponse.setName(company.getName());
		comResponse.setScale(company.getScale());
		comResponse.setBusinesses(businessTypeService.findById(company.getBussinessTypeId()));
		comResponse.setCity(cityService.findById(company.getCityId()));
		comResponse.setDistrict(districtService.findById(company.getDistrictId()));
		comResponse.setAddress(company.getAddress());
		comResponse.setLogo(company.getLogoUrl());
		comResponse.setCoverImage(company.getCoverImageUrl());
		comResponse.setIntroduction(company.getIntroduction());
		comResponse.setIsPublised(company.getIsPublised());
		
		return comResponse;
	}
}
