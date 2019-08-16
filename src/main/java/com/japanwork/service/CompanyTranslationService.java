package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.japanwork.exception.BadRequestException;
import com.japanwork.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.payload.request.CompanyTranslationRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company_tranlation.CompanyTranslationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CompanyTranslationService {
	@Autowired
	private CompanyTranslationRepository companyTranslationRepository;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private TranslatorService translatorService;

	public CompanyTranslation save(
	        Company company,
            Translator translator,
            Language language,
            CompanyTranslationRequest companyRequest
    ) throws ServerError{
		try {
			Date date           = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());

			CompanyTranslation companyTranslation = new CompanyTranslation();
            companyTranslation.setCompany(company);
            companyTranslation.setTranslator(translator);
            companyTranslation.setLanguage(language);
            companyTranslation.setName(companyRequest.getName());
            companyTranslation.setAddress(companyRequest.getAddress());
            companyTranslation.setIntroduction(companyRequest.getIntroduction());
            companyTranslation.setStatus(1);
            companyTranslation.setCreatedAt(timestamp);
            companyTranslation.setUpdatedAt(timestamp);
            companyTranslation.setDeletedAt(null);

			CompanyTranslation result = companyTranslationRepository.save(companyTranslation);
			return result;
		} catch (Exception e) {
			throw new BadRequestException(MessageConstant.CREATE_COMPANY_TRANSLATE_FAIL);
		}
	}

	public CompanyTranslation update(CompanyTranslationRequest companyTranslationRequest, UUID id, UserPrincipal userPrincipal)
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());

			CompanyTranslation company = new CompanyTranslation();

			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_TRANSLATOR")) {
				company = companyTranslationRepository.findByIdAndDeletedAt(id, null);
				if(company == null) {
					throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
				}
				if(!(company.getTranslator()).getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			} else {
				company = companyTranslationRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}

			company.setName(companyTranslationRequest.getName());
			company.setAddress(companyTranslationRequest.getAddress());
			company.setIntroduction(companyTranslationRequest.getIntroduction());
			company.setStatus(1);
			company.setUpdatedAt(timestamp);

			CompanyTranslation result = companyTranslationRepository.save(company);
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (ForbiddenException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.UPDATE_COMPANY_TRANSLATE_FAIL);
		}
	}

	public CompanyResponse convertCompanyResponse(CompanyTranslation companyTranslation) {
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
