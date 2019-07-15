package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Academy;
import com.japanwork.model.Business;
import com.japanwork.model.Candidate;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.District;
import com.japanwork.model.Experience;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.model.LanguageCertificateType;
import com.japanwork.model.Level;
import com.japanwork.model.User;
import com.japanwork.payload.request.AcademyRequest;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.payload.request.CandidateWishRequest;
import com.japanwork.payload.request.ExperienceRequest;
import com.japanwork.payload.request.LanguageCertificateRequest;
import com.japanwork.payload.response.CandidateResponse;
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
	
	public Candidate savePersonal(CandidatePersonalRequest candidatePersonalRequest, UserPrincipal userPrincipal)
			throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Candidate candidate = new Candidate();
			candidate.setUser(userService.findById(userPrincipal.getId()));
			candidate.setFullName(candidatePersonalRequest.getFullName());
			candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
			candidate.setGender(candidatePersonalRequest.getGender());
			candidate.setMarital(candidatePersonalRequest.getMarital());
			candidate.setResidentalCity(new City(candidatePersonalRequest.getResidentalCityId()));
			candidate.setResidentalDistrict(new District(candidatePersonalRequest.getResidentalDistrictId()));
			candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddres());
			candidate.setAvatar(candidatePersonalRequest.getAvatar());
			candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
			candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setStatusInfo(1);
			candidate.setCreateDate(timestamp);
			candidate.setUpdateDate(timestamp);
			candidate.setDelete(false);
			
			Candidate result = candidateRepository.save(candidate);
			userService.changePropertyId(userPrincipal.getId(), result.getId());
			return result;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_CREATE_FAIL);
		}
	}
	
	public Candidate updatePersonal(CandidatePersonalRequest candidatePersonalRequest, UUID id, 
			UserPrincipal userPrincipal) throws ResourceNotFoundException, ServerError{
			try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Candidate candidate = new Candidate();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
				candidate = this.findByIdAndIsDelete(id);
			} else {
				candidate = candidateRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}
	
			candidate.setFullName(candidatePersonalRequest.getFullName());
			candidate.setDateOfBirth(candidatePersonalRequest.getDateOfBirth());
			candidate.setGender(candidatePersonalRequest.getGender());
			candidate.setMarital(candidatePersonalRequest.getMarital());
			candidate.setResidentalCity(new City(candidatePersonalRequest.getResidentalCityId()));
			candidate.setResidentalDistrict(new District(candidatePersonalRequest.getResidentalDistrictId()));
			candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddres());
			candidate.setAvatar(candidatePersonalRequest.getAvatar());
			candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
			candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setUpdateDate(timestamp);
			
			Candidate result = candidateRepository.save(candidate);	
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_UPDATE_PERSONAL_FAIL);
		}
	}
	
	public Candidate updateWish(CandidateWishRequest candidateWishRequest, UUID id,UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Candidate candidate = new Candidate();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
				candidate = this.findByIdAndIsDelete(id);
			} else {
				candidate = candidateRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}
	
			candidate.setWishWorkingCity(new City(candidateWishRequest.getWishWorkingCityId()));
			candidate.setWishWorkingDistrict(new District(candidateWishRequest.getWishWorkingDistrictId()));
			candidate.setWishWorkingAddress(candidateWishRequest.getWishWorkingAddress());
			candidate.setWishBusiness(new Business(candidateWishRequest.getWishBusinessId()));
			candidate.setWishLevel(new Level(candidateWishRequest.getWishLevelId()));
			candidate.setWishContract(new Contract(candidateWishRequest.getWishContractId()));
			candidate.setWishSalary(candidateWishRequest.getWishSalary());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setStatusInfo(2);
			candidate.setUpdateDate(timestamp);
			
			Candidate result = candidateRepository.save(candidate);	
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_UPDATE_WISH_FAIL);
		}
	}
	
	@Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRES_NEW)
	public Candidate updateExperience(CandidateExperienceRequest candidateExperienceRequest, @PathVariable UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		try {	
			this.deleteExperiencer(id);
		
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
			
			if(!candidateExperienceRequest.getAcademies().isEmpty()) {
				List<Academy> listAcademy = new ArrayList<>();
				for (AcademyRequest academyRequest : candidateExperienceRequest.getAcademies()) {
					Academy academy = new Academy();
					
					academy.setCandidateId(id);
					academy.setAcademyCenterName(academyRequest.getAcademyCenterName());
					academy.setMajorName(academyRequest.getMajorName());
					academy.setGrade(academyRequest.getGrade());
					academy.setGradeSystem(academyRequest.getGradeSystem());
					academy.setStartDate(academyRequest.getStartDate());
					academy.setEndDate(academyRequest.getEndDate());
					academy.setCreateDate(timestamp);
					academy.setUpdateDate(timestamp);
					academy.setDelete(false);
					
					listAcademy.add(academy);
				}
				Set<Academy> result = new HashSet<Academy>(academyService.saveAll(listAcademy));
				candidate.setAcademies(result);
			}
			
			if(!candidateExperienceRequest.getExperiences().isEmpty()) {
				List<Experience> listExperience = new ArrayList<>();
				for (ExperienceRequest experienceRequest : candidateExperienceRequest.getExperiences()) {
					Experience experience = new Experience();
					
					experience.setCandidateId(id);
					experience.setOrganizaion(experienceRequest.getOrganizaion());
					experience.setDesc(experienceRequest.getDesc());
					experience.setLevel(new Level(experienceRequest.getLevelId()));
					experience.setBusiness(new Business(experienceRequest.getBusinessId()));
					experience.setStartDate(experienceRequest.getStartDate());
					experience.setEndDate(experienceRequest.getEndDate());
					experience.setCreateDate(timestamp);
					experience.setUpdateDate(timestamp);
					experience.setDelete(false);
					
					listExperience.add(experience);
				}
				
				Set<Experience> result = new HashSet<Experience>(experienceService.saveAll(listExperience));
				candidate.setExperiences(result);
			}
			
			if(!candidateExperienceRequest.getLanguageCertificates().isEmpty()) {
				List<LanguageCertificate> listLanguageCertificate = new ArrayList<>();
				for (LanguageCertificateRequest languageCertificateRequest : candidateExperienceRequest.getLanguageCertificates()) {
					LanguageCertificate languageCertificate = new LanguageCertificate();
	
					languageCertificate.setCandidateId(id);
					languageCertificate.setScore(languageCertificateRequest.getScore());
					languageCertificate.setLanguageCertificateType(new LanguageCertificateType(languageCertificateRequest.getLanguageCertificateTypeId()));
					languageCertificate.setTakenDate(languageCertificateRequest.getTakenDate());
					languageCertificate.setCreateDate(timestamp);
					languageCertificate.setUpdateDate(timestamp);
					languageCertificate.setDelete(false);
					
					listLanguageCertificate.add(languageCertificate);
				}
				
				Set<LanguageCertificate> result = new HashSet<LanguageCertificate>(languageCertificateService.saveAll(listLanguageCertificate));
				candidate.setLanguageCertificates(result);
			}
				
			return candidate;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_UPDATE_EXPERIENCE_FAIL);
		}
	}
	
	public Candidate isDel(UUID id, boolean isDel) throws ResourceNotFoundException, ServerError{
		try {
			Candidate candidate = candidateRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			candidate.setDelete(isDel);
			candidateRepository.save(candidate);
			Candidate result = candidateRepository.findByIdAndIsDelete(id, false);	
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if(isDel) {
				throw new ServerError(MessageConstant.CANDIDATE_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.CANDIDATE_UN_DELETE_FAIL);
			}
			
		}
		
	}
	
	public Candidate findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByIdAndIsDelete(id, false);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
			
		return candidate;
	}
	
	public Candidate myCandidate(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByUserAndIsDelete(userService.findById(userPrincipal.getId()), false);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
			
		return candidate;
	}
	
	public boolean checkCandidateByUser(User user){
		Candidate candidate = candidateRepository.findByUser(user);
		if(candidate == null) {
			return false;
		}
		 return true;
	}
	
	public List<Candidate> findAllByIsDelete() {
		List<Candidate> listCandidate = candidateRepository.findAllByIsDelete(false);
		return listCandidate;
	}
	
	public Page<Candidate> findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Candidate> pages = candidateRepository.findAllByIsDelete(PageRequest.of(page-1, paging), false);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	private void deleteExperiencer(UUID id) {
		academyService.del(id);
		experienceService.del(id);
		languageCertificateService.del(id);
	}
	
	public CandidateResponse convertCandiateResponse(Candidate candidate) {
		CandidateResponse candidateResponse = new CandidateResponse();
		
		candidateResponse.setId(candidate.getId());
		candidateResponse.setFullName(candidate.getFullName());
		candidateResponse.setDateOfBirth(candidate.getDateOfBirth());
		candidateResponse.setGender(candidate.getGender());
		candidateResponse.setMarital(candidate.getMarital());
		candidateResponse.setResidentalCityId(candidate.getResidentalCity().getId());
		candidateResponse.setResidentalDistrictId(candidate.getResidentalDistrict().getId());
		candidateResponse.setResidentalAddres(candidate.getResidentalAddres());
		candidateResponse.setAvatar(candidate.getAvatar());
		candidateResponse.setIntroduction(candidate.getIntroduction());
		candidateResponse.setJapaneseLevel(candidate.getJapaneseLevel());
		
		if(candidate.getWishWorkingCity() != null) {
			candidateResponse.setWishWorkingCityId(candidate.getWishWorkingCity().getId());
		}
		
		if(candidate.getWishWorkingDistrict() != null) {
			candidateResponse.setWishWorkingDistrictId(candidate.getWishWorkingDistrict().getId());
		}
		
		candidateResponse.setWishWorkingAddress(candidate.getWishWorkingAddress());
		
		if(candidate.getWishBusiness() != null) {
			candidateResponse.setWishBusinessId(candidate.getWishBusiness().getId());
		}
		
		if(candidate.getWishLevel() != null) {
			candidateResponse.setWishLevelId(candidate.getWishLevel().getId());
		}
		
		if(candidate.getWishContract() != null) {
			candidateResponse.setWishContractId(candidate.getWishContract().getId());
		}
		
		candidateResponse.setWishSalary(candidate.getWishSalary());

		candidateResponse.setAcademies(academyService.listAcademyResponse(candidate.getAcademies()));
		candidateResponse.setExperiences(experienceService.listExperienceResponse(candidate.getExperiences()));
		candidateResponse.setLanguageCertificates(languageCertificateService.listLanguageCertificateResponse(candidate.getLanguageCertificates()));
		
		return candidateResponse;
	}
}
