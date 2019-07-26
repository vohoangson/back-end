package com.japanwork.support;

import java.util.UUID;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFound;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Company;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.model.Language;
import com.japanwork.repository.language.LanguageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonSupport {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public void loadCompany(UUID id) throws ResourceNotFoundException {
        Company company = companyRepository.findByIdAndDeletedAt(id, null);
        if(company == null) {
            throw new ResourceNotFound(
                    MessageConstant.COMPANY_NOT_FOUND_CODE,
                    MessageConstant.COMPANY_NOT_FOUND
            );
        }
    }

    public void loadLanguage(UUID id) throws ResourceNotFoundException {
        Language language = languageRepository.findByIdAndDeletedAt(id, null);
        if(language == null) {
            throw new ResourceNotFound(
                    MessageConstant.LANGUAGE_NOT_FOUND_CODE,
                    MessageConstant.LANGUAGE_NOT_FOUND
            );
        }
    }
}
