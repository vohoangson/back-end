package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.payload.request.CandidateAcademyRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.academy.AcademyRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class AcademyService {
	@Autowired
	private AcademyRepository academyRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CandidateService candidateService;
	
	public BaseDataResponse save(CandidateAcademyRequest candidateAcademyRequest, UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Academy academy = new Academy();
		academy.setCandidateId(id);
		academy.setAcademyCenterName(candidateAcademyRequest.getAcademyCenterName());
		academy.setMajorName(candidateAcademyRequest.getMajorName());
		academy.setGrade(candidateAcademyRequest.getGrade());
		academy.setGradeSystem(candidateAcademyRequest.getGradeSystem());
		academy.setStartDate(candidateAcademyRequest.getStartDate());
		academy.setEndDate(candidateAcademyRequest.getEndDate());
		academy.setCreateDate(timestamp);
		academy.setUpdateDate(timestamp);
		academy.setDelete(false);
		
		Academy result = academyRepository.save(academy);
		
		candidateService.updateStatusInfo(id,1);		
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(CandidateAcademyRequest candidateAcademyRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Academy academy = new Academy();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CADIDATE")) {
			academy = academyRepository.findByIdAndIsDelete(id, false);
			if(academy == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
		} else {
			academy = academyRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		academy.setAcademyCenterName(candidateAcademyRequest.getAcademyCenterName());
		academy.setMajorName(candidateAcademyRequest.getMajorName());
		academy.setGrade(candidateAcademyRequest.getGrade());
		academy.setGradeSystem(candidateAcademyRequest.getGradeSystem());
		academy.setStartDate(candidateAcademyRequest.getStartDate());
		academy.setEndDate(candidateAcademyRequest.getEndDate());
		academy.setUpdateDate(timestamp);
		
		Academy result = academyRepository.save(academy);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Academy academy = academyRepository.findByIdAndIsDelete(id, false);
		if(academy == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		academy.setDelete(true);
		Academy result = academyRepository.save(academy);
		
		List<Academy> listAcademy = academyRepository.findByCandidateIdAndIsDelete(academy.getCandidateId(), false);
		System.out.println(listAcademy.size());
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
