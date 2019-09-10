package com.japanwork.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void del(UUID id) {
		List<Experience> listExperience = experienceRepository.findByCandidateId(id);
		if(listExperience.size() > 0) {
			experienceRepository.deleteAll(listExperience);
		}
	}
}
