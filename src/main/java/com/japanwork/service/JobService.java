package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Job;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private LevelService levelService;
	@Autowired
	private BusinessService businessService;
	@Autowired 
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	
	public BaseDataResponse findAllByIsDelete() {
		List<Job> listJob = jobRepository.findAllByIsDelete(false);
		
		List<JobResponse> listJobResponses = new ArrayList<JobResponse>();
		for (Job item : listJob) {
			JobResponse jobResponse = this.setJobResponse(item);
			listJobResponses.add(jobResponse);
		}
		BaseDataResponse response = new BaseDataResponse(listJobResponses);
		
		return response;
	}
	
	public BaseDataResponse save(JobRequest jobRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Job job = new Job();
		job.setName(jobRequest.getName());
		job.setCompanyId(jobRequest.getCompany().getId());
		job.setContractTypeId(jobRequest.getContract().getId());
		job.setBusinessTypeId(jobRequest.getBusiness().getId());
		job.setLevelId(jobRequest.getLevel().getId());
		job.setWorkPlaceCityId(jobRequest.getCity().getId());
		job.setWorkPlaceDistrictId(jobRequest.getDistrict().getId());
		job.setWorkPlaceAddress(jobRequest.getAddress());
		job.setDescription(jobRequest.getDescription());
		job.setSkillRequirement(jobRequest.getSkillRequirement());
		job.setBenefit(jobRequest.getBenefit());
		job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
		job.setMinSalary(jobRequest.getMinSalary());
		job.setMaxSalary(jobRequest.getMaxSalary());
		job.setStatus(jobRequest.getStatus());		
		job.setCreateDate(timestamp);
		job.setUpdateDate(timestamp);
		job.setDelete(false);
		
		Job result = jobRepository.save(job);
		JobResponse jobResponse = this.setJobResponse(result);
		BaseDataResponse response = new BaseDataResponse(jobResponse);		
		return response;
	}
	
	public BaseDataResponse update(JobRequest jobRequest, UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Job job = new Job();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
			job = jobRepository.findByIdAndIsDelete(id, false);
			if(job == null) {
				throw new ResourceNotFoundException("Job not found for this id :: " + id);
			}
		} else {
			job = jobRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Job not found for this id :: " + id));
		}

		job.setName(jobRequest.getName());
		job.setCompanyId(jobRequest.getCompany().getId());
		job.setContractTypeId(jobRequest.getContract().getId());
		job.setBusinessTypeId(jobRequest.getBusiness().getId());
		job.setLevelId(jobRequest.getLevel().getId());
		job.setWorkPlaceCityId(jobRequest.getCity().getId());
		job.setWorkPlaceDistrictId(jobRequest.getDistrict().getId());
		job.setWorkPlaceAddress(jobRequest.getAddress());
		job.setDescription(jobRequest.getDescription());
		job.setSkillRequirement(jobRequest.getSkillRequirement());
		job.setBenefit(jobRequest.getBenefit());
		job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
		job.setMinSalary(jobRequest.getMinSalary());
		job.setMaxSalary(jobRequest.getMaxSalary());
		job.setStatus(jobRequest.getStatus());
		job.setUpdateDate(timestamp);
		
		Job result = jobRepository.save(job);

		JobResponse jobResponse = this.setJobResponse(result);
		
		BaseDataResponse response = new BaseDataResponse(jobResponse);		
		return response;
	}
	
	public BaseDataResponse del(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findByIdAndIsDelete(id, false);
		if(job == null) {
			throw new ResourceNotFoundException("Job not found for this id :: " + id);
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
				.orElseThrow(() -> new ResourceNotFoundException("Job not found for this id :: " + id));
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
			throw new ResourceNotFoundException("Job not found for this id :: " + id);
		}
		JobResponse jobResponse = this.setJobResponse(job);
		
		BaseDataResponse response = new BaseDataResponse(jobResponse);
		
		return response;
	}
	
	private JobResponse setJobResponse(Job job) {
		JobResponse jobResponse = new JobResponse();
		
		jobResponse.setId(job.getId());
		jobResponse.setCompany(companyService.convertCompany(job.getId()));
		jobResponse.setName(job.getName());
		jobResponse.setContract(contractService.convertContract(job.getContractTypeId()));
		jobResponse.setBusiness(businessService.convertBusiness(job.getBusinessTypeId()));
		jobResponse.setLevel(levelService.convertLevel(job.getLevelId()));
		jobResponse.setSkillRequirement(job.getSkillRequirement());
		jobResponse.setJapaneseLevel(job.getJapaneseLevelRequirement());
		jobResponse.setDescription(job.getDescription());
		jobResponse.setCity(cityService.convertCity(job.getWorkPlaceCityId()));
		jobResponse.setDistrict(districtService.convertDistrict(job.getWorkPlaceDistrictId()));
		jobResponse.setAddress(job.getWorkPlaceAddress());
		jobResponse.setMinSalary(job.getMinSalary());
		jobResponse.setMaxSalary(job.getMaxSalary());
		jobResponse.setBenefit(job.getBenefit());
		jobResponse.setStatus(job.getStatus());
		return jobResponse;
	}
}
