package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.District;
import com.japanwork.model.User;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserService userService;
	
	public Company save(CompanyRequest companyRequest, UserPrincipal userPrincipal) throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Company company = new Company();
			company.setUser(userService.findById(userPrincipal.getId()));
			company.setName(companyRequest.getName());
			company.setScale(companyRequest.getScale());
			company.setBusinesses(Business.listBusiness(companyRequest.getBusinessIds()));
			company.setCity(new City(companyRequest.getCityId()));
			company.setDistrict(new District(companyRequest.getDistrictId()));
			company.setAddress(companyRequest.getAddress());
			company.setCoverImageUrl(companyRequest.getCoverImage());
			company.setLogoUrl(companyRequest.getLogo());
			company.setIntroduction(companyRequest.getIntroduction());
			company.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			company.setCreateDate(timestamp);
			company.setUpdateDate(timestamp);
			company.setDelete(false);
			
			Company result = companyRepository.save(company);
			userService.changePropertyId(userPrincipal.getId(), result.getId());		
			return result;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.COMPANY_CREATE_FAIL);
		}
	}
	
	public Company update(CompanyRequest companyRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Company company = new Company();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
				company = companyRepository.findByIdAndIsDelete(id, false);
				if(company == null) {
					throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
				}
				if(!company.getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			} else {
				company = companyRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}
	
			company.setName(companyRequest.getName());
			company.setScale(companyRequest.getScale());
			company.setBusinesses(Business.listBusiness(companyRequest.getBusinessIds()));
			company.setCity(new City(companyRequest.getCityId()));
			company.setDistrict(new District(companyRequest.getDistrictId()));
			company.setAddress(companyRequest.getAddress());
			company.setCoverImageUrl(companyRequest.getCoverImage());
			company.setLogoUrl(companyRequest.getLogo());
			company.setIntroduction(companyRequest.getIntroduction());
			company.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			company.setUpdateDate(timestamp);
			
			Company result = companyRepository.save(company);		
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (ForbiddenException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.COMPANY_UPDATE_FAIL);
		}
	}
	
	public Company isDel(UUID id, boolean isDel) throws ResourceNotFoundException{
		Company company = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
		company.setDelete(isDel);
		companyRepository.save(company);
		Company result = companyRepository.findByIdAndIsDelete(id, false);	
		return result;
	}
	
	public void del(Company company){
		companyRepository.delete(company);
	}
	
	public Company findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndIsDelete(id, false);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return company;
	}
	
	public Company myCompany(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Company company = this.findByUserAndIsDelete(userService.findById(userPrincipal.getId()), false);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return company;
	}
	
	public boolean checkCompanyByUser(User user){
		Company company = companyRepository.findByUserAndIsDelete(user, false);
		if(company == null) {
			return false;
		}
		 return true;
	}
	
	public Page<Company> findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByIsDelete(PageRequest.of(page-1, paging), false);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Company findById(UUID id) {
		return companyRepository.findById(id).get();
	}
	
	public Company findByUserAndIsDelete(User user, boolean isDelete){
		Company company = companyRepository.findByUserAndIsDelete(user, isDelete);
		return company;
	}
	
	public Company findByUser(User user){
		Company company = companyRepository.findByUser(user);
		return company;
	}
	
	public CompanyResponse convertCompanyResponse(Company company) {
		CompanyResponse companyResponse = new CompanyResponse(
				company.getId(),
				company.getName(), 
				company.getScale(), 
				Business.listBusinessID(company.getBusinesses()),
				company.getCity().getId(), 
				company.getDistrict().getId(), 
				company.getAddress(), 
				company.getLogoUrl(), 
				company.getCoverImageUrl(),
				company.getIntroduction());
		return companyResponse;
	}
}
