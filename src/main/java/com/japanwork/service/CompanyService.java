package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.japanwork.exception.ResourceNotFound;
import com.japanwork.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
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

	public Company show(UUID id, UUID language_id) throws ResourceNotFound {
	    try {
            Company company = companyRepository.findByIdAndDeletedAt(id, null);
            return company;
        } catch(Exception e) {
            throw new ResourceNotFound(
                    MessageConstant.COMPANY_NOT_FOUND_CODE,
                    MessageConstant.COMPANY_NOT_FOUND
            );
        }
    }

	public Page<Company> companiesByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids,null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}

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
			company.setCreatedAt(timestamp);
			company.setUpdatedAt(timestamp);
			company.setDeletedAt(null);

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
				company = companyRepository.findByIdAndDeletedAt(id, null);
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
			company.setUpdatedAt(timestamp);

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

	public Company isDel(UUID id, Timestamp deletedAt) throws ResourceNotFoundException, ServerError{
		try {
			Company company = companyRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			company.setDeletedAt(deletedAt);
			companyRepository.save(company);
			Company result = companyRepository.findByIdAndDeletedAt(id, null);
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.COMPANY_DELETE_FAIL_MSG);
			} else {
				throw new ServerError(MessageConstant.COMPANY_UN_DELETE_FAIL_MSG);
			}

		}
	}

	public void del(Company company){
		companyRepository.delete(company);
	}

	public Company findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Company company = companyRepository.findByIdAndDeletedAt(id, null);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return company;
	}

	public Company myCompany(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Company company = this.findByUserAndIsDelete(userService.findById(userPrincipal.getId()), null);
		if(company == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return company;
	}

	public boolean checkCompanyByUser(User user){
		Company company = companyRepository.findByUserAndDeletedAt(user, null);
		if(company == null) {
			return false;
		}
		 return true;
	}

	public Page<Company> findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Company> pages = companyRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}

	public Company findById(UUID id) {
		return companyRepository.findById(id).get();
	}

	public Company findByUserAndIsDelete(User user, Timestamp deletedAt){
		Company company = companyRepository.findByUserAndDeletedAt(user, null);
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
