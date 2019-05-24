package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Candidate;
import com.japanwork.model.User;
import com.japanwork.payload.request.CandidateInfoRequest;
import com.japanwork.payload.request.CandidateJobRequest;
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
	
	public BaseDataResponse saveInfo(CandidateInfoRequest candidateInfoRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Candidate candidate = new Candidate();
		candidate.setUser(userService.findById(userPrincipal.getId()));
		candidate.setFullName(candidateInfoRequest.getFullName());
		candidate.setDateOfBirth(candidateInfoRequest.getDateOfBirth());
		candidate.setGender(candidateInfoRequest.getGender());
		candidate.setMarital(candidateInfoRequest.getMarital());
		candidate.setResidentalCity(candidateInfoRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidateInfoRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidateInfoRequest.getResidentalAddres());
		candidate.setAvatar(candidateInfoRequest.getAvatar());
		candidate.setIntroduction(candidateInfoRequest.getIntroduction());
		candidate.setJapaneseLevel(candidateInfoRequest.getJapaneseLevel());
		candidate.setStatus("untranslated");
		candidate.setStatusInfo(1);
		candidate.setCreateDate(timestamp);
		candidate.setUpdateDate(timestamp);
		candidate.setDelete(false);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse updateInfo(CandidateInfoRequest candidateInfoRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
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

		candidate.setFullName(candidateInfoRequest.getFullName());
		candidate.setDateOfBirth(candidateInfoRequest.getDateOfBirth());
		candidate.setGender(candidateInfoRequest.getGender());
		candidate.setMarital(candidateInfoRequest.getMarital());
		candidate.setResidentalCity(candidateInfoRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidateInfoRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidateInfoRequest.getResidentalAddres());
		candidate.setAvatar(candidateInfoRequest.getAvatar());
		candidate.setIntroduction(candidateInfoRequest.getIntroduction());
		candidate.setJapaneseLevel(candidateInfoRequest.getJapaneseLevel());
		candidate.setStatus("untranslated");
		candidate.setUpdateDate(timestamp);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse updateJob(CandidateJobRequest candidateJobRequest, @PathVariable UUID id,UserPrincipal userPrincipal) throws ResourceNotFoundException{
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

		candidate.setWishWorkingCity(candidateJobRequest.getWishWorkingCity());
		candidate.setWishWorkingDistrict(candidateJobRequest.getWishWorkingDistrict());
		candidate.setWishWorkingAddress(candidateJobRequest.getWishWorkingAddress());
		candidate.setWishBusiness(candidateJobRequest.getWishBusiness());
		candidate.setWishLevel(candidateJobRequest.getWishLevel());
		candidate.setWishContract(candidateJobRequest.getWishContract());
		candidate.setWishSalary(candidateJobRequest.getWishSalary());
		candidate.setStatus("untranslated");
		candidate.setStatusInfo(candidate.getStatusInfo()+1);
		candidate.setUpdateDate(timestamp);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public Candidate updateStatusInfo(UUID id, int status) {
		Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
		candidate.setStatusInfo(candidate.getStatusInfo() + status);
		return candidateRepository.save(candidate);
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
	
	public Candidate findCandidateByIdAndIsDelete(UUID id){
		Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
		return candidate;
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
