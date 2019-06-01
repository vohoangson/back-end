package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.LanguageCertificateType;
import com.japanwork.payload.request.LanguageCertificateTypeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.language_certificate_type.LanguageCertificateTypeRepository;

@Service
public class LanguageCertificateTypeService {
	@Autowired
	private LanguageCertificateTypeRepository languageCertificateTypeRepository;
	
	public BaseDataResponse save(LanguageCertificateTypeRequest languageCertificateTypeRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		LanguageCertificateType LanguageCertificateType = new LanguageCertificateType();
		LanguageCertificateType.setNameJa(languageCertificateTypeRequest.getNameJa());
		LanguageCertificateType.setNameVi(languageCertificateTypeRequest.getNameVi());
		LanguageCertificateType.setDescription(languageCertificateTypeRequest.getDescription());
		LanguageCertificateType.setCreateDate(timestamp);
		LanguageCertificateType.setUpdateDate(timestamp);
		LanguageCertificateType.setDelete(false);
		
		LanguageCertificateType result = languageCertificateTypeRepository.save(LanguageCertificateType);
		BaseDataResponse response = new BaseDataResponse(result);	
		return response;
	}
	
	public BaseDataResponse findAllByIsDelete() {
		List<LanguageCertificateType> list = languageCertificateTypeRepository.findAllByIsDelete(false);
		
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		LanguageCertificateType languageCertificateType = languageCertificateTypeRepository.findByIdAndIsDelete(id, false);
		if(languageCertificateType == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(languageCertificateType);	
		return response;
	}
}
