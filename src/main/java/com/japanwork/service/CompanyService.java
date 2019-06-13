package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.UnauthorizedException;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.District;
import com.japanwork.model.PageInfo;
import com.japanwork.model.User;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserService userService;
	
	public BaseDataResponse save(CompanyRequest companyRequest, UserPrincipal userPrincipal){
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		company.setUser(userService.findById(userPrincipal.getId()));
		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBusinesses(Business.convertBusiness(companyRequest.getBusinessIds()));
		company.setCity(new City(companyRequest.getCityId()));
		company.setDistrict(new District(companyRequest.getDistrictId()));
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		company.setCreateDate(timestamp);
		company.setUpdateDate(timestamp);
		company.setDelete(false);
		
		Company result = companyRepository.save(company);
		BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(result));		
		return response;
	}
	
	public BaseDataResponse update(CompanyRequest companyRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, UnauthorizedException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
			company = companyRepository.findByIdAndIsDelete(id, false);
			if(company == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
			if(!company.getUser().getId().equals(userPrincipal.getId())) {
				throw new UnauthorizedException(MessageConstant.ERROR_403);
			}
		} else {
			company = companyRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBusinesses(Business.convertBusiness(companyRequest.getBusinessIds()));
		company.setCity(new City(companyRequest.getCityId()));
		company.setDistrict(new District(companyRequest.getDistrictId()));
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		company.setUpdateDate(timestamp);
		
		Company result = companyRepository.save(company);
		BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(result));		
		return response;
	}
	
	public BaseDataResponse isDel(UUID id, boolean isDel) throws ResourceNotFoundException{
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		company.setDelete(isDel);
		companyRepository.save(company);
		Company result = companyRepository.findByIdAndIsDelete(id, false);
		BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(result));	
		return response;
	}
	
	public void del(Company company){
		companyRepository.delete(company);
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndIsDelete(id, false);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(convertCompanyResponse(company));	
		return response;
	}
	
	public boolean checkCompanyByUser(User user) throws ResourceNotFoundException{
		Company company = companyRepository.findByUser(user);
		if(company == null) {
			return false;
		}
		 return true;
	}
	
	public BaseDataMetaResponse findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByIsDelete(PageRequest.of(page-1, paging), false);
			PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
			List<CompanyResponse> list = new ArrayList<CompanyResponse>();
			
			if(pages.getContent().size() > 0) {
				for (Company company : pages.getContent()) {
					list.add(convertCompanyResponse(company));
				}
			}
			
			BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
			return response;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Company findById(UUID id) {
		return companyRepository.findById(id).get();
	}
	
	private CompanyResponse convertCompanyResponse(Company company) {
		CompanyResponse companyResponse = new CompanyResponse(
				company.getName(), 
				company.getScale(), 
				Business.convertBusinessIDs(company.getBusinesses()),
				company.getCity().getId(), 
				company.getDistrict().getId(), 
				company.getAddress(), 
				company.getLogoUrl(), 
				company.getCoverImageUrl());
		return companyResponse;
	}
}
