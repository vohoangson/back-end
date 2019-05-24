package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.payload.request.CandidateLangugeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.language_certificate.LanguageCertificateRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class LanguageCertificateService {
	@Autowired
	private LanguageCertificateRepository languageCertificateRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CandidateService candidateService;
	
	public BaseDataResponse save(CandidateLangugeRequest candidateLangugeRequest, UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		LanguageCertificate languageCertificate = new LanguageCertificate();
		languageCertificate.setCandidateId(id);
		languageCertificate.setScore(candidateLangugeRequest.getScore());
		languageCertificate.setLanguageCertificateType(candidateLangugeRequest.getLanguageCertificateType());
		languageCertificate.setTakenDate(candidateLangugeRequest.getTakenDate());
		languageCertificate.setCreateDate(timestamp);
		languageCertificate.setUpdateDate(timestamp);
		languageCertificate.setDelete(false);
		
		LanguageCertificate result = languageCertificateRepository.save(languageCertificate);
		
		candidateService.updateStatusInfo(id,1);		
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(CandidateLangugeRequest candidateLangugeRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		LanguageCertificate languageCertificate = new LanguageCertificate();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
			languageCertificate = languageCertificateRepository.findByIdAndIsDelete(id, false);
			if(languageCertificate == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
		} else {
			languageCertificate = languageCertificateRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}
		
		languageCertificate.setScore(candidateLangugeRequest.getScore());
		languageCertificate.setLanguageCertificateType(candidateLangugeRequest.getLanguageCertificateType());
		languageCertificate.setTakenDate(candidateLangugeRequest.getTakenDate());
		languageCertificate.setUpdateDate(timestamp);
		
		LanguageCertificate result = languageCertificateRepository.save(languageCertificate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
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
