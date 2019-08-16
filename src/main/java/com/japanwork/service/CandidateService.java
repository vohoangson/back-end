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
import com.japanwork.payload.request.CandidateExpectedRequest;
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
			candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
			candidate.setAvatar(candidatePersonalRequest.getAvatar());
			candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
			candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setStatusInfo(1);
			candidate.setCreatedAt(timestamp);
			candidate.setUpdatedAt(timestamp);
			candidate.setDeletedAt(null);
			
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
			candidate.setResidentalAddres(candidatePersonalRequest.getResidentalAddress());
			candidate.setAvatar(candidatePersonalRequest.getAvatar());
			candidate.setIntroduction(candidatePersonalRequest.getIntroduction());
			candidate.setJapaneseLevel(candidatePersonalRequest.getJapaneseLevel());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setUpdatedAt(timestamp);
			
			Candidate result = candidateRepository.save(candidate);	
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_UPDATE_PERSONAL_FAIL);
		}
	}
	
	public Candidate updateWish(CandidateExpectedRequest candidateExpectedRequest, UUID id,UserPrincipal userPrincipal) 
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
	
			candidate.setExpectedWorkingCity(new City(candidateExpectedRequest.getExpectedWorkingCityId()));
			candidate.setExpectedWorkingDistrict(new District(candidateExpectedRequest.getExpectedWorkingDistrictId()));
			candidate.setExpectedWorkingAddress(candidateExpectedRequest.getExpectedWorkingAddress());
			candidate.setExpectedBusiness(new Business(candidateExpectedRequest.getExpectedBusinessId()));
			candidate.setExpectedLevel(new Level(candidateExpectedRequest.getExpectedLevelId()));
			candidate.setExpectedContract(new Contract(candidateExpectedRequest.getExpectedContractId()));
			candidate.setExpectedSalary(candidateExpectedRequest.getExpectedSalary());
			candidate.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			candidate.setStatusInfo(2);
			candidate.setUpdatedAt(timestamp);
			
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
			
			Candidate candidate = candidateRepository.findByIdAndDeletedAt(id, null);
			
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
					academy.setCreatedAt(timestamp);
					academy.setUpdatedAt(timestamp);
					academy.setDeletedAt(null);
					
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
					experience.setCreatedAt(timestamp);
					experience.setUpdatedAt(timestamp);
					experience.setDeletedAt(null);
					
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
					languageCertificate.setCreatedAt(timestamp);
					languageCertificate.setUpdatedAt(timestamp);
					languageCertificate.setDeletedAt(null);
					
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
	
	public Candidate isDel(UUID id, Timestamp deletedAt) throws ResourceNotFoundException, ServerError{
		try {
			Candidate candidate = candidateRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			candidate.setDeletedAt(deletedAt);
			candidateRepository.save(candidate);
			Candidate result = candidateRepository.findByIdAndDeletedAt(id, null);	
			return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.CANDIDATE_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.CANDIDATE_UN_DELETE_FAIL);
			}
			
		}
		
	}
	
	public Candidate findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByIdAndDeletedAt(id, null);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
			
		return candidate;
	}
	
	public Candidate myCandidate(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByUserAndDeletedAt(userService.findById(userPrincipal.getId()), null);
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
		List<Candidate> listCandidate = candidateRepository.findAllByDeletedAt(null);
		return listCandidate;
	}
	
	public Page<Candidate> findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Candidate> pages = candidateRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Page<Candidate> candidatesByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Candidate> pages = candidateRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids, null);
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
		
		if(candidate.getExpectedWorkingCity() != null) {
			candidateResponse.setExpectedWorkingCityId(candidate.getExpectedWorkingCity().getId());
		}
		
		if(candidate.getExpectedWorkingDistrict() != null) {
			candidateResponse.setExpectedWorkingDistrictId(candidate.getExpectedWorkingDistrict().getId());
		}
		
		candidateResponse.setExpectedWorkingAddress(candidate.getExpectedWorkingAddress());
		
		if(candidate.getExpectedBusiness() != null) {
			candidateResponse.setExpectedBusinessId(candidate.getExpectedBusiness().getId());
		}
		
		if(candidate.getExpectedLevel() != null) {
			candidateResponse.setExpectedLevelId(candidate.getExpectedLevel().getId());
		}
		
		if(candidate.getExpectedContract() != null) {
			candidateResponse.setExpectedContractId(candidate.getExpectedContract().getId());
		}
		
		candidateResponse.setExpectedSalary(candidate.getExpectedSalary());

		candidateResponse.setAcademies(academyService.listAcademyResponse(candidate.getAcademies()));
		candidateResponse.setExperiences(experienceService.listExperienceResponse(candidate.getExperiences()));
		candidateResponse.setLanguageCertificates(languageCertificateService.listLanguageCertificateResponse(candidate.getLanguageCertificates()));
		
		return candidateResponse;
	}
}
