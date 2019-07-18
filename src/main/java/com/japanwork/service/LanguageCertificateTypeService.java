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
import com.japanwork.repository.language_certificate_type.LanguageCertificateTypeRepository;

@Service
public class LanguageCertificateTypeService {
	@Autowired
	private LanguageCertificateTypeRepository languageCertificateTypeRepository;
	
	public LanguageCertificateType save(LanguageCertificateTypeRequest languageCertificateTypeRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		LanguageCertificateType LanguageCertificateType = new LanguageCertificateType();
		LanguageCertificateType.setJa(languageCertificateTypeRequest.getJa());
		LanguageCertificateType.setVi(languageCertificateTypeRequest.getVi());
		LanguageCertificateType.setDesc(languageCertificateTypeRequest.getDesc());
		LanguageCertificateType.setCreatedAt(timestamp);
		LanguageCertificateType.setUpdatedAt(timestamp);
		LanguageCertificateType.setDeletedAt(null);
		
		LanguageCertificateType result = languageCertificateTypeRepository.save(LanguageCertificateType);
		return result;
	}
	
	public List<LanguageCertificateType> findAllByIsDelete() {
		List<LanguageCertificateType> list = languageCertificateTypeRepository.findAllByDeletedAt(null);
		return list;
	}
	
	public LanguageCertificateType findByIdAndIsDelete(UUID id) {
		LanguageCertificateType languageCertificateType = languageCertificateTypeRepository.findByIdAndDeletedAt(id, null);
		if(languageCertificateType == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}	
		return languageCertificateType;
	}
}
