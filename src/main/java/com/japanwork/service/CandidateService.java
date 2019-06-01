package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.model.Candidate;
import com.japanwork.model.Experience;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.model.User;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.payload.request.CandidateWishRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.CandidateExperienceRespone;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class CandidateService {
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AcademyService academyService;
	
	@Autowired
	private ExperienceService experienceService;
	
	@Autowired
	private LanguageCertificateService languageCertificateService;
	
	public BaseDataResponse savePersonal(CandidatePersonalRequest candidatePersonalRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Candidate candidate = new Candidate();
		candidate.setUser(userService.findById(userPrincipal.getId()));
		candidate.setFullName(candidatePersonalRequest.getFullName());
		candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
		candidate.setGender(candidatePersonalRequest.getGender());
		candidate.setMarital(candidatePersonalRequest.getMarital());
		candidate.setResidentalCity(candidatePersonalRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidatePersonalRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddres());
		candidate.setAvatar(candidatePersonalRequest.getAvatar());
		candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
		candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
		candidate.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		candidate.setStatusInfo(1);
		candidate.setCreateDate(timestamp);
		candidate.setUpdateDate(timestamp);
		candidate.setDelete(false);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse updatePersonal(CandidatePersonalRequest candidatePersonalRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
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

		candidate.setFullName(candidatePersonalRequest.getFullName());
		candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
		candidate.setGender(candidatePersonalRequest.getGender());
		candidate.setMarital(candidatePersonalRequest.getMarital());
		candidate.setResidentalCity(candidatePersonalRequest.getResidentalCity());
		candidate.setResidentalDistrict(candidatePersonalRequest.getResidentalDistrict());
		candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddres());
		candidate.setAvatar(candidatePersonalRequest.getAvatar());
		candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
		candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
		candidate.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		candidate.setUpdateDate(timestamp);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse updateWish(CandidateWishRequest candidateJobRequest, UUID id,UserPrincipal userPrincipal) throws ResourceNotFoundException{
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
		candidate.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		candidate.setStatusInfo(2);
		candidate.setUpdateDate(timestamp);
		
		Candidate result = candidateRepository.save(candidate);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse createExperience(CandidateExperienceRequest candidateExperienceRequest, UUID id) throws ResourceNotFoundException{		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		CandidateExperienceRespone candidateExperienceRespone = new CandidateExperienceRespone();
		
		if(!candidateExperienceRequest.getAcademies().isEmpty()) {
			List<Academy> listAcademy = new ArrayList<>();
			for (Academy academy : candidateExperienceRequest.getAcademies()) {
				academy.setCandidateId(id);
				academy.setCreateDate(timestamp);
				academy.setUpdateDate(timestamp);
				academy.setDelete(false);
				
				listAcademy.add(academy);
			}
			List<Academy> result = academyService.saveAll(listAcademy);
			candidateExperienceRespone.setAcademies(result);
		}
		
		if(!candidateExperienceRequest.getExperiences().isEmpty()) {
			List<Experience> listExperience = new ArrayList<>();
			for (Experience experience : candidateExperienceRequest.getExperiences()) {
				experience.setCandidateId(id);
				experience.setCreateDate(timestamp);
				experience.setUpdateDate(timestamp);
				experience.setDelete(false);
				
				listExperience.add(experience);
			}
			
			List<Experience> result = experienceService.saveAll(listExperience);
			candidateExperienceRespone.setExperiences(result);
		}
		
		if(!candidateExperienceRequest.getLanguageCertificates().isEmpty()) {
			List<LanguageCertificate> listLanguageCertificate = new ArrayList<>();
			for (LanguageCertificate languageCertificate : candidateExperienceRequest.getLanguageCertificates()) {
				languageCertificate.setCandidateId(id);
				languageCertificate.setCreateDate(timestamp);
				languageCertificate.setUpdateDate(timestamp);
				languageCertificate.setDelete(false);
				
				listLanguageCertificate.add(languageCertificate);
			}
			
			List<LanguageCertificate> result = languageCertificateService.saveAll(listLanguageCertificate);
			candidateExperienceRespone.setLanguageCertificates(result);
		}
		
		BaseDataResponse response = new BaseDataResponse(candidateExperienceRespone);		
		return response;
	}
	
	public BaseDataResponse updateExperience(CandidateExperienceRequest candidateExperienceRequest, @PathVariable UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		this.deleteExperiencer(id);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		CandidateExperienceRespone candidateExperienceRespone = new CandidateExperienceRespone();
		
		if(!candidateExperienceRequest.getAcademies().isEmpty()) {
			List<Academy> listAcademy = new ArrayList<>();
			for (Academy academy : candidateExperienceRequest.getAcademies()) {
				academy.setCandidateId(id);
				academy.setCreateDate(timestamp);
				academy.setUpdateDate(timestamp);
				academy.setDelete(false);
				
				listAcademy.add(academy);
			}
			List<Academy> result = academyService.saveAll(listAcademy);
			candidateExperienceRespone.setAcademies(result);
		}
		
		if(!candidateExperienceRequest.getExperiences().isEmpty()) {
			List<Experience> listExperience = new ArrayList<>();
			for (Experience experience : candidateExperienceRequest.getExperiences()) {
				experience.setCandidateId(id);
				experience.setCreateDate(timestamp);
				experience.setUpdateDate(timestamp);
				experience.setDelete(false);
				
				listExperience.add(experience);
			}
			
			List<Experience> result = experienceService.saveAll(listExperience);
			candidateExperienceRespone.setExperiences(result);
		}
		
		if(!candidateExperienceRequest.getLanguageCertificates().isEmpty()) {
			List<LanguageCertificate> listLanguageCertificate = new ArrayList<>();
			for (LanguageCertificate languageCertificate : candidateExperienceRequest.getLanguageCertificates()) {
				languageCertificate.setCandidateId(id);
				languageCertificate.setCreateDate(timestamp);
				languageCertificate.setUpdateDate(timestamp);
				languageCertificate.setDelete(false);
				
				listLanguageCertificate.add(languageCertificate);
			}
			
			List<LanguageCertificate> result = languageCertificateService.saveAll(listLanguageCertificate);
			candidateExperienceRespone.setLanguageCertificates(result);
		}
		
		BaseDataResponse response = new BaseDataResponse(candidateExperienceRespone);		
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
	
	private void deleteExperiencer(UUID id) {
		academyService.del(id);
		experienceService.del(id);
		languageCertificateService.del(id);
	}
}
