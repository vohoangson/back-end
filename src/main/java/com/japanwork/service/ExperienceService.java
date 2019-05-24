package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Experience;
import com.japanwork.payload.request.CandidateExperiencesRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.experience.ExperienceRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class ExperienceService {
	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CandidateService candidateService;
	
	public BaseDataResponse save(CandidateExperiencesRequest candidateExperiencesRequest, UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Experience experience = new Experience();
		experience.setCandidateId(id);
		experience.setOrganizaion(candidateExperiencesRequest.getOrganizaion());
		experience.setDescription(candidateExperiencesRequest.getDescription());
		experience.setLevel(candidateExperiencesRequest.getLevel());
		experience.setBusiness(candidateExperiencesRequest.getBusiness());
		experience.setStartDate(candidateExperiencesRequest.getStartDate());
		experience.setEndDate(candidateExperiencesRequest.getEndDate());
		experience.setCreateDate(timestamp);
		experience.setUpdateDate(timestamp);
		experience.setDelete(false);
		
		Experience result = experienceRepository.save(experience);
		
		candidateService.updateStatusInfo(id,1);		
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(CandidateExperiencesRequest candidateExperiencesRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Experience experience = new Experience();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
			experience = experienceRepository.findByIdAndIsDelete(id, false);
			if(experience == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
		} else {
			experience = experienceRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		experience.setOrganizaion(candidateExperiencesRequest.getOrganizaion());
		experience.setDescription(candidateExperiencesRequest.getDescription());
		experience.setLevel(candidateExperiencesRequest.getLevel());
		experience.setBusiness(candidateExperiencesRequest.getBusiness());
		experience.setStartDate(candidateExperiencesRequest.getStartDate());
		experience.setEndDate(candidateExperiencesRequest.getEndDate());
		experience.setUpdateDate(timestamp);
		
		Experience result = experienceRepository.save(experience);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Experience experience = experienceRepository.findByIdAndIsDelete(id, false);
		if(experience == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		experience.setDelete(true);
		Experience result = experienceRepository.save(experience);
		
		List<Experience> listExperience = experienceRepository.findByCandidateIdAndIsDelete(experience.getCandidateId(), false);
		if(listExperience.size() == 0) {
			candidateService.updateStatusInfo(experience.getCandidateId(),-1);
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
