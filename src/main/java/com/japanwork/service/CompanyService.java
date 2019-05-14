package com.japanwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.Company;
import com.japanwork.repository.company.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
}
