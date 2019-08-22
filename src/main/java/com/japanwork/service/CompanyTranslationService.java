package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Business;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Language;
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
            Language language,
            CompanyTranslationRequest companyRequest
    ) throws ServerError{
        try {
            CompanyTranslation companyTranslation = new CompanyTranslation();
            companyTranslation.setCompany(company);
            companyTranslation.setLanguage(language);
            companyTranslation.setName(companyRequest.getName());
            companyTranslation.setAddress(companyRequest.getAddress());
            companyTranslation.setIntroduction(companyRequest.getIntroduction());
            companyTranslation.setStatus(1);
            companyTranslation.setCreatedAt(CommonFunction.dateTimeNow());
            companyTranslation.setUpdatedAt(null);
            companyTranslation.setDeletedAt(null);

			CompanyTranslation result = companyTranslationRepository.save(companyTranslation);
			return result;
		} catch (Exception e) {
			throw new BadRequestException(MessageConstant.COMPANY_TRANSLATE_CREATE_FAIL);
		}
	}

//	public CompanyTranslation update(CompanyTranslationRequest companyTranslationRequest, UUID id, UserPrincipal userPrincipal)
//			throws ResourceNotFoundException2, ServerError{
//		try {
//			CompanyTranslation company = companyTranslationRepository.findByIdAndDeletedAt(id, null);
//
//			if(company == null) {
//				throw new ResourceNotFoundException2(MessageConstant.ERROR_404_MSG);
//			}
//
//			company.setName(companyTranslationRequest.getName());
//			company.setAddress(companyTranslationRequest.getAddress());
//			company.setIntroduction(companyTranslationRequest.getIntroduction());
//			company.setStatus(1);
//			company.setUpdatedAt(CommonFunction.dateTimeNow());
//
//			CompanyTranslation result = companyTranslationRepository.save(company);
//			return result;
//		} catch (ResourceNotFoundException2 e) {
//			throw e;
//		} catch (Exception e) {
//			throw new ServerError(MessageConstant.UPDATE_COMPANY_TRANSLATE_FAIL);
//		}
//	}

	public CompanyResponse convertCompanyResponse(CompanyTranslation companyTranslation) {
		Company company = companyTranslation.getCompany();
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
