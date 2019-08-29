package com.japanwork.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Company;
import com.japanwork.repository.company.CompanyRepository;

@Service
public class IndexService {
	@Autowired
	private CompanyRepository companyRepository;
	
	public ResponseDataAPI index(int page, int paging) throws ResourceNotFoundException {
        try {
            Page<Company> pages = companyRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
            return pages;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
