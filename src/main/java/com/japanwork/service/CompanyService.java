package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.District;
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

	public Company create(CompanyRequest companyRequest, UserPrincipal userPrincipal) throws ServerError{
		try {
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
			company.setCreatedAt(CommonFunction.dateTimeNow());
			company.setUpdatedAt(null);
			company.setDeletedAt(null);

			Company result = companyRepository.save(company);
			userService.changePropertyId(userPrincipal.getId(), result.getId());
			return result;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.COMPANY_CREATE_FAIL);
		}
	}

	public Company update(CompanyRequest companyRequest, Company company, UserPrincipal userPrincipal)
			throws ForbiddenException, ServerError{
		try {
			if(!company.getUser().getId().equals(userPrincipal.getId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
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
			company.setUpdatedAt(CommonFunction.dateTimeNow());

			Company result = companyRepository.save(company);
			return result;
		} catch (ForbiddenException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.COMPANY_UPDATE_FAIL);
		}
	}

	public Page<Company> index(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}
	
	public Company isDel(UUID id, Timestamp deletedAt) throws ServerError{
		try {
			Company company = companyRepository.findById(id).get();
			company.setDeletedAt(deletedAt);
			companyRepository.save(company);
			Company result = companyRepository.findByIdAndDeletedAt(id, null);
			return result;
		} catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.COMPANY_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.COMPANY_UNDELETE_FAIL);
			}

		}
	}

	public Page<Company> companiesByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids,null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
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
