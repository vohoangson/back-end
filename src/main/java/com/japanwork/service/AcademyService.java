package com.japanwork.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.payload.response.AcademyResponse;
import com.japanwork.repository.academy.AcademyRepository;

@Service
public class AcademyService {
	@Autowired
	private AcademyRepository academyRepository;
	
	public List<Academy> saveAll(List<Academy> academies) {
		List<Academy> result = academyRepository.saveAll(academies);						
		return result;
	}
	
	public Set<AcademyResponse> listAcademyResponse(Set<Academy> academies){
		if(academies != null) {
			Set<AcademyResponse> list = new HashSet<AcademyResponse>();
			for (Academy academy : academies) {
				AcademyResponse academyResponse = new AcademyResponse();
				academyResponse.setAcademyCenterName(academy.getAcademyCenterName());
				academyResponse.setMajorName(academy.getMajorName());
				academyResponse.setGrade(academy.getGrade());
				academyResponse.setGradeSystem(academy.getGradeSystem());
				academyResponse.setStartDate(academy.getStartDate());
				academyResponse.setEndDate(academy.getEndDate());
				
				list.add(academyResponse);
			}
			return list;
		}
		return null;
	}
	
	public void del(UUID id) throws ResourceNotFoundException{		
		List<Academy> listAcademy = academyRepository.findByCandidateId(id);
		if(listAcademy.size() > 0) {
			academyRepository.deleteAll(listAcademy);
		}
	}
}
