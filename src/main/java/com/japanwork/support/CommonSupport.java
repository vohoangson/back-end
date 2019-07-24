package com.japanwork.support;

import java.util.UUID;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Company;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.language.LanguageRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class CommonSupport {
    @Autowired
    private CompanyRepository companyRepository;

    public void loadCompany(UUID id) {
        try {
            Company company = companyRepository.findByIdAndDeletedAt(id, null);
        } catch (Exception e) {
            throw new ServerError(MessageConstant.CREATE_COMPANY_TRANSLATE_FAIL);
        }
    }
}
