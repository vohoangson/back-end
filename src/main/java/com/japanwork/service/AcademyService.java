package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.repository.academy.AcademyRepository;

@Service
public class AcademyService {
	@Autowired
	private AcademyRepository academyRepository;
	
	public List<Academy> saveAll(List<Academy> academies) {
		List<Academy> result = academyRepository.saveAll(academies);						
		return result;
	}
	
	
	public void del(UUID id) throws ResourceNotFoundException{		
		List<Academy> listAcademy = academyRepository.findByCandidateId(id);
		if(listAcademy.size() > 0) {
			academyRepository.deleteAll(listAcademy);
		}
	}
}
