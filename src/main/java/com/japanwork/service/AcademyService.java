package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.academy.AcademyRepository;

@Service
public class AcademyService {
	@Autowired
	private AcademyRepository academyRepository;
	
	@Autowired
	private CandidateService candidateService;
	
	public List<Academy> saveAll(List<Academy> academies) {
		List<Academy> result = academyRepository.saveAll(academies);						
		return result;
	}
	
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Academy academy = academyRepository.findByIdAndIsDelete(id, false);
		if(academy == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		academy.setDelete(true);
		Academy result = academyRepository.save(academy);
		
		List<Academy> listAcademy = academyRepository.findByCandidateIdAndIsDelete(academy.getCandidateId(), false);
		if(listAcademy.size() == 0) {
			candidateService.updateStatusInfo(academy.getCandidateId(),-1);
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
