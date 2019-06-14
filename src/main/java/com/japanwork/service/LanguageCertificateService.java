package com.japanwork.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.payload.response.LanguageCertificateResponse;
import com.japanwork.repository.language_certificate.LanguageCertificateRepository;

@Service
public class LanguageCertificateService {
	@Autowired
	private LanguageCertificateRepository languageCertificateRepository;
	
	public List<LanguageCertificate> saveAll(List<LanguageCertificate> languageCertificates) {
		List<LanguageCertificate> result = languageCertificateRepository.saveAll(languageCertificates);		
		return result;
	}
	
	public Set<LanguageCertificateResponse> listLanguageCertificateResponse(Set<LanguageCertificate> languageCertificates){
		if(languageCertificates != null) {
			Set<LanguageCertificateResponse> list = new HashSet<LanguageCertificateResponse>();
			for (LanguageCertificate languageCertificate : languageCertificates) {
				LanguageCertificateResponse languageCertificateResponse = new LanguageCertificateResponse();
				
				languageCertificateResponse.setScore(languageCertificate.getScore());
				languageCertificateResponse.setLanguageCertificateTypeId(languageCertificate.getLanguageCertificateType().getId());
				languageCertificateResponse.setTakenDate(languageCertificate.getTakenDate());
				
				list.add(languageCertificateResponse);
			}
			return list;
		}
		return null;
	}
	
	public void del(UUID id) throws ResourceNotFoundException{		
		List<LanguageCertificate> listLanguageCertificate = languageCertificateRepository.findByCandidateId(id);
		if(listLanguageCertificate.size() > 0) {
			languageCertificateRepository.deleteAll(listLanguageCertificate);
		}
	}
}
