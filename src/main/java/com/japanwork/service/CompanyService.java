package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Company;
import com.japanwork.model.PageInfo;
import com.japanwork.model.User;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserService userService;
	
	public BaseDataResponse save(CompanyRequest companyRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		company.setUser(userService.findById(userPrincipal.getId()));
		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBusinesses(companyRequest.getBusinesses());
		company.setCountry(companyRequest.getCountry());
		company.setCity(companyRequest.getCity());
		company.setDistrict(companyRequest.getDistrict());
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		company.setCreateDate(timestamp);
		company.setUpdateDate(timestamp);
		company.setDelete(false);
		
		Company result = companyRepository.save(company);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(CompanyRequest companyRequest, UUID id, UserPrincipal userPrincipal, HttpServletResponse httpServletResponse) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Company company = new Company();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
			company = companyRepository.findByIdAndIsDelete(id, false);
			if(company == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
			if(!company.getUser().getId().equals(userPrincipal.getId())) {
				 httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.ERROR_401, MessageConstant.ERROR_403);
				BaseDataResponse response = new BaseDataResponse(baseMessageResponse);		
				return response;
			}
		} else {
			company = companyRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBusinesses(companyRequest.getBusinesses());
		company.setCountry(companyRequest.getCountry());
		company.setCity(companyRequest.getCity());
		company.setDistrict(companyRequest.getDistrict());
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		company.setUpdateDate(timestamp);
		
		Company result = companyRepository.save(company);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse isDel(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndIsDelete(id, false);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
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
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndIsDelete(id, false);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
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
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
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
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(company);	
		return response;
	}
	
	public boolean checkCompanyByUser(User user) throws ResourceNotFoundException{
		Company company = companyRepository.findByUser(user);
		if(company == null) {
			return false;
		}
		 return true;
	}
	
	public BaseDataMetaResponse findAllByIsDelete(int page, int paging) {
		Page<Company> pages = companyRepository.findAllByIsDelete(PageRequest.of(page-1, paging), false);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		BaseDataMetaResponse response = new BaseDataMetaResponse(pages.getContent(), pageInfo);
		return response;
	}
	
	public Company findById(UUID id) {
		return companyRepository.findById(id).get();
	}
}
