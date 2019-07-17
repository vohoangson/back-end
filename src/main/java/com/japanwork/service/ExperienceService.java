package com.japanwork.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Experience;
import com.japanwork.payload.response.ExperienceResponse;
import com.japanwork.repository.experience.ExperienceRepository;

@Service
public class ExperienceService {
	@Autowired
	private ExperienceRepository experienceRepository;
	
	public List<Experience> saveAll(List<Experience> experiences) {
		List<Experience> result = experienceRepository.saveAll(experiences);		
		return result;
	}
	
	public Set<ExperienceResponse> listExperienceResponse(Set<Experience> experiences){
		Set<ExperienceResponse> list = new HashSet<ExperienceResponse>();
		if(experiences != null) {
			for (Experience experience : experiences) {
				ExperienceResponse experienceResponse = new ExperienceResponse();
				experienceResponse.setOrganizaion(experience.getOrganizaion());
				experienceResponse.setDesc(experience.getDesc());
				experienceResponse.setLevelId(experience.getLevel().getUid());
				experienceResponse.setBusinessId(experience.getBusiness().getUid());
				experienceResponse.setStartDate(experience.getStartDate());
				experienceResponse.setEndDate(experience.getEndDate());
				
				list.add(experienceResponse);
			}
		}
		return list;
	}
	
	public void del(UUID id) throws ResourceNotFoundException{		
		List<Experience> listExperience = experienceRepository.findByCandidateId(id);
		if(listExperience.size() > 0) {
			experienceRepository.deleteAll(listExperience);
		}
	}
}
