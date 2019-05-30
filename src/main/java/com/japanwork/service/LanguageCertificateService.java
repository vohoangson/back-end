package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.language_certificate.LanguageCertificateRepository;

@Service
public class LanguageCertificateService {
	@Autowired
	private LanguageCertificateRepository languageCertificateRepository;
	
	@Autowired
	private CandidateService candidateService;
	
	public List<LanguageCertificate> saveAll(List<LanguageCertificate> languageCertificates) {
		List<LanguageCertificate> result = languageCertificateRepository.saveAll(languageCertificates);		
		return result;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		LanguageCertificate languageCertificate = languageCertificateRepository.findByIdAndIsDelete(id, false);
		if(languageCertificate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		languageCertificate.setDelete(true);
		LanguageCertificate result = languageCertificateRepository.save(languageCertificate);
		
		List<LanguageCertificate> listAcademy = languageCertificateRepository.findByCandidateIdAndIsDelete(languageCertificate.getCandidateId(), false);
		if(listAcademy.size() == 0) {
			candidateService.updateStatusInfo(languageCertificate.getCandidateId(),-1);
		}
		
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
}
