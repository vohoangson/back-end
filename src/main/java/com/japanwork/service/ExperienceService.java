package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Experience;
import com.japanwork.repository.experience.ExperienceRepository;

@Service
public class ExperienceService {
	@Autowired
	private ExperienceRepository experienceRepository;
	
	public List<Experience> saveAll(List<Experience> experiences) {
		List<Experience> result = experienceRepository.saveAll(experiences);		
		return result;
	}
	
	public void del(UUID id) throws ResourceNotFoundException{		
		List<Experience> listExperience = experienceRepository.findByCandidateId(id);
		if(listExperience.size() > 0) {
			experienceRepository.deleteAll(listExperience);
		}
	}
}
