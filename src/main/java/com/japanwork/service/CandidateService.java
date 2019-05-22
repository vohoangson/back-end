package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Candidate;
import com.japanwork.model.User;
import com.japanwork.payload.request.CandidateRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CandidateService {
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private UserService userService;
	
	public BaseDataResponse save(CandidateRequest candidateRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Candidate candidate = new Candidate();
		candidate.setUser(userService.findById(userPrincipal.getId()));
		candidate.setFullName(candidateRequest.getFullName());
		candidate.setGender(candidateRequest.getGender());
		candidate.setResidentalCity(candidateRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidateRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidateRequest.getResidentalAddres());
		candidate.setAvatar(candidateRequest.getAvatar());
		candidate.setIntroduction(candidateRequest.getIntroduction());
		candidate.setJapaneseLevel(candidateRequest.getJapaneseLevel());
		candidate.setLanguageCertificate(candidateRequest.getLanguageCertificate());
		candidate.setWishWorkingCity(candidateRequest.getWishWorkingCity());
		candidate.setWishWorkingDistrict(candidateRequest.getWishWorkingDistrict());
		candidate.setWishWorkingAddress(candidateRequest.getWishWorkingAddress());
		candidate.setWishBusiness(candidateRequest.getWishBusiness());
		candidate.setWishLevel(candidateRequest.getWishLevel());
		candidate.setWishContract(candidateRequest.getWishContract());
		candidate.setWishSalary(candidateRequest.getWishSalary());
		candidate.setStatus(candidateRequest.getStatus());
		candidate.setCreateDate(timestamp);
		candidate.setUpdateDate(timestamp);
		candidate.setDelete(false);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(CandidateRequest candidateRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Candidate candidate = new Candidate();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
			candidate = candidateRepository.findByIdAndIsDelete(id, false);
			if(candidate == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
		} else {
			candidate = candidateRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		candidate.setFullName(candidateRequest.getFullName());
		candidate.setGender(candidateRequest.getGender());
		candidate.setResidentalCity(candidateRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidateRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidateRequest.getResidentalAddres());
		candidate.setAvatar(candidateRequest.getAvatar());
		candidate.setIntroduction(candidateRequest.getIntroduction());
		candidate.setJapaneseLevel(candidateRequest.getJapaneseLevel());
		candidate.setLanguageCertificate(candidateRequest.getLanguageCertificate());
		candidate.setWishWorkingCity(candidateRequest.getWishWorkingCity());
		candidate.setWishWorkingDistrict(candidateRequest.getWishWorkingDistrict());
		candidate.setWishWorkingAddress(candidateRequest.getWishWorkingAddress());
		candidate.setWishBusiness(candidateRequest.getWishBusiness());
		candidate.setWishLevel(candidateRequest.getWishLevel());
		candidate.setWishContract(candidateRequest.getWishContract());
		candidate.setWishSalary(candidateRequest.getWishSalary());
		candidate.setStatus(candidateRequest.getStatus());
		candidate.setUpdateDate(timestamp);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		candidate.setDelete(true);
		Candidate result = candidateRepository.save(candidate);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse unDel(UUID id) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		candidate.setDelete(false);
		Candidate result = candidateRepository.save(candidate);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(candidate);	
		return response;
	}
	
	public boolean checkCandidateByUser(User user) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByUser(user);
		if(candidate == null) {
			return false;
		}
		 return true;
	}
	
	public BaseDataResponse findAllByIsDelete() {
		List<Candidate> listCandidate = candidateRepository.findAllByIsDelete(false);
		
		BaseDataResponse response = new BaseDataResponse(listCandidate);
		return response;
	}
}
