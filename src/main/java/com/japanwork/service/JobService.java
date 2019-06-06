package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Job;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private UserService userService;
	
	public BaseDataResponse findAllByIsDelete() {
		List<Job> listJob = jobRepository.findAllByIsDelete(false);
		BaseDataResponse response = new BaseDataResponse(listJob);
		return response;
	}
	
	public BaseDataMetaResponse findAllByIsDelete(int page, int paging) {
		Page<Job> pages = jobRepository.findAllByIsDelete(PageRequest.of(page-1, paging), false);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		BaseDataMetaResponse response = new BaseDataMetaResponse(pages.getContent(), pageInfo);
		return response;
	}
	
	
	public BaseDataResponse save(JobRequest jobRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Job job = new Job();
		job.setName(jobRequest.getName());
		job.setCompany(jobRequest.getCompany());
		job.setBusinesses(jobRequest.getBusinesses());
		job.setContract(jobRequest.getContract());
		job.setLevel(jobRequest.getLevel());
		job.setCountry(jobRequest.getCountry());
		job.setCity(jobRequest.getCity());
		job.setDistrict(jobRequest.getDistrict());
		job.setAddress(jobRequest.getAddress());
		job.setDescription(jobRequest.getDescription());
		job.setSkillRequirement(jobRequest.getSkillRequirement());
		job.setBenefit(jobRequest.getBenefit());
		job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
		job.setApplicationDeadline(jobRequest.getApplicationDeadline());
		job.setCurrencyUnit(jobRequest.getCurrencyUnit());
		job.setMinSalary(jobRequest.getMinSalary());
		job.setMaxSalary(jobRequest.getMaxSalary());
		job.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);		
		job.setCreateDate(timestamp);
		job.setUpdateDate(timestamp);
		job.setDelete(false);
		
		Job result = jobRepository.save(job);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(JobRequest jobRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Job job = new Job();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
			job = jobRepository.findByIdAndIsDelete(id, false);
			if(job == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404);
			}
		} else {
			job = jobRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		}

		job.setName(jobRequest.getName());
		job.setCompany(jobRequest.getCompany());
		job.setBusinesses(jobRequest.getBusinesses());
		job.setContract(jobRequest.getContract());
		job.setLevel(jobRequest.getLevel());
		job.setCountry(jobRequest.getCountry());
		job.setCity(jobRequest.getCity());
		job.setDistrict(jobRequest.getDistrict());
		job.setAddress(jobRequest.getAddress());
		job.setDescription(jobRequest.getDescription());
		job.setSkillRequirement(jobRequest.getSkillRequirement());
		job.setBenefit(jobRequest.getBenefit());
		job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
		job.setApplicationDeadline(jobRequest.getApplicationDeadline());
		job.setCurrencyUnit(jobRequest.getCurrencyUnit());
		job.setMinSalary(jobRequest.getMinSalary());
		job.setMaxSalary(jobRequest.getMaxSalary());
		job.setStatus(MessageConstant.STATUS_TRANSLATE_UNTRANSLATE);
		job.setUpdateDate(timestamp);
		
		Job result = jobRepository.save(job);		
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findByIdAndIsDelete(id, false);
		if(job == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		job.setDelete(true);
		Job result = jobRepository.save(job);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.DELETE, MessageConstant.DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse unDel(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
		job.setDelete(false);
		Job result = jobRepository.save(job);
		if(result != null) {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_SUCCESS);
			return new BaseDataResponse(deleteResponse);
		} else {
			BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.UN_DELETE, MessageConstant.UN_DEL_FAIL);
			return new BaseDataResponse(deleteResponse);
		}
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findByIdAndIsDelete(id, false);
		if(job == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}		
		
		BaseDataResponse response = new BaseDataResponse(job);
		return response;
	}
}
