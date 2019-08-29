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
import com.japanwork.model.User;
import com.japanwork.payload.request.CompanyRequest;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.company_tranlation.CompanyTranslationRepository;
import com.japanwork.service.UserService;

@Service
public class CreateCompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyTranslationRepository companyTranslationRepository;
	
	@Autowired
	private UserService userService;
	
	public CompanyResponse perform(CompanyRequest companyRequest, User user, City city, District district) {
		//Create company
		Company company = new Company();
		company.setUser(user);
		company.setName(companyRequest.getName());
		company.setScale(companyRequest.getScale());
		company.setBusinesses(Business.listBusiness(companyRequest.getBusinessIds()));
		company.setCity(city);
		company.setDistrict(district);
		company.setAddress(companyRequest.getAddress());
		company.setCoverImageUrl(companyRequest.getCoverImage());
		company.setLogoUrl(companyRequest.getLogo());
		company.setIntroduction(companyRequest.getIntroduction());
		company.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
		company.setCreatedAt(CommonFunction.getCurrentDateTime());
		company.setUpdatedAt(CommonFunction.getCurrentDateTime());

		Company result = companyRepository.save(company);
		
		//Set propertyId for user
		userService.changePropertyId(user.getId(), result .getId());
		
		//Create company translation
		CompanyTranslation companyTranslation = createCompanyTranslation(company, user);
		
		return new CompanyResponse().companyFullSerializer(result, companyTranslation);
	}
	
	public CompanyTranslation createCompanyTranslation(Company company, User user) {
		
		CompanyTranslation companyTranslation = new CompanyTranslation();
        companyTranslation.setCompany(company);
        companyTranslation.setLanguage(user.getCountry().getLanguage());
        companyTranslation.setName(company.getName());
        companyTranslation.setAddress(company.getAddress());
        companyTranslation.setIntroduction(company.getIntroduction());
        companyTranslation.setStatus(1);
        companyTranslation.setCreatedAt(CommonFunction.getCurrentDateTime());
        companyTranslation.setUpdatedAt(CommonFunction.getCurrentDateTime());

        CompanyTranslation result = companyTranslationRepository.save(companyTranslation);
        return result;
	}
	
}
