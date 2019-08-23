package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.LanguageCertificateType;
import com.japanwork.payload.request.LanguageCertificateTypeRequest;
import com.japanwork.repository.language_certificate_type.LanguageCertificateTypeRepository;

@Service
public class LanguageCertificateTypeService {
	@Autowired
	private LanguageCertificateTypeRepository languageCertificateTypeRepository;

	public LanguageCertificateType create(LanguageCertificateTypeRequest languageCertificateTypeRequest) {
		LanguageCertificateType LanguageCertificateType = new LanguageCertificateType();
		LanguageCertificateType.setJa(languageCertificateTypeRequest.getJa());
		LanguageCertificateType.setVi(languageCertificateTypeRequest.getVi());
		LanguageCertificateType.setDesc(languageCertificateTypeRequest.getDesc());
		LanguageCertificateType.setCreatedAt(CommonFunction.getCurrentDateTime());
		LanguageCertificateType.setUpdatedAt(CommonFunction.getCurrentDateTime());

		LanguageCertificateType result = languageCertificateTypeRepository.save(LanguageCertificateType);
		return result;
	}

	public List<LanguageCertificateType> index() {
		List<LanguageCertificateType> list = languageCertificateTypeRepository.findAllByDeletedAt(null);
		return list;
	}

	public LanguageCertificateType show(UUID id) {
		LanguageCertificateType languageCertificateType = languageCertificateTypeRepository.findByIdAndDeletedAt(id, null);
		return languageCertificateType;
	}
}
