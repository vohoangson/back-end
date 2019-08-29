package com.japanwork.service.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.repository.company_tranlation.CompanyTranslationRepository;

@Service
public class IndexCompanyService {
	@Autowired
	private CompanyTranslationRepository companyTranslationRepository;
	
	public ResponseDataAPI perform(int page, int paging, Language language) throws ResourceNotFoundException {
        try {
            CompanyResponse companyResponse = new CompanyResponse();

            Page<CompanyTranslation> pages = companyTranslationRepository.findAllByLanguageAndDeletedAt(PageRequest.of(page-1, paging), language, null);
            PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
            List<CompanyResponse> list = new ArrayList<CompanyResponse>();

            if(pages.getContent().size() > 0) {
    			for (CompanyTranslation companyTranslation : pages.getContent()) {
    				list.add( companyResponse.companyFullSerializer(companyTranslation.getCompany(), companyTranslation));
    			}
    		}
            
            return new ResponseDataAPI(
    				CommonConstant.ResponseDataAPIStatus.SUCCESS,
    				list,
    				pageInfo
            );
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
