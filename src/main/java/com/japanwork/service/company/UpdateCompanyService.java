package com.japanwork.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.District;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.company_tranlation.CompanyTranslationRepository;

@Service
public class UpdateCompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyTranslationRepository companyTranslationRepository;
	
	public CompanyResponse perform(CompanyRequest companyRequest, Company company ) {
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
		company.setUpdatedAt(CommonFunction.getCurrentDateTime());

		Company result = companyRepository.save(company);
		CompanyTranslation companyTranslation = updateCompanyTranslation(company);
		
		return new CompanyResponse().companyFullSerializer(result, companyTranslation);
	}
	
	public CompanyTranslation updateCompanyTranslation(Company company) {
		CompanyTranslation companyTranslation = new CompanyTranslation();
        companyTranslation.setName(company.getName());
        companyTranslation.setAddress(company.getAddress());
        companyTranslation.setIntroduction(company.getIntroduction());
        companyTranslation.setUpdatedAt(CommonFunction.getCurrentDateTime());

        CompanyTranslation result = companyTranslationRepository.save(companyTranslation);
        return result;
	}
}
