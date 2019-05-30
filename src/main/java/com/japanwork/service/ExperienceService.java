package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Experience;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.experience.ExperienceRepository;

@Service
public class ExperienceService {
	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CandidateService candidateService;
	
	public List<Experience> saveAll(List<Experience> experiences) {
		List<Experience> result = experienceRepository.saveAll(experiences);		
		return result;
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
