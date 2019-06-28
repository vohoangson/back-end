package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.District;
import com.japanwork.model.Job;
import com.japanwork.model.Level;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.JobFilterRequest;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepositoryIplm;
import com.japanwork.security.UserPrincipal;

@Service
public class JobService {
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Autowired
	private JobRepositoryIplm jobRepository;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	public BaseDataMetaResponse findAllByIsDelete(JobFilterRequest jobFilterRequest, int page, int paging) 
			throws IllegalArgumentException{
		try {			
			long totalElements = jobRepository.countFilterJob(jobFilterRequest, page, paging, false);
			int totalPage = (int)totalElements / paging;
			if((totalElements % paging) > 0) {
				totalPage ++;
			}
			List<Job> pages = jobRepository.filterJob(jobFilterRequest, totalPage, paging, false);
			
			PageInfo pageInfo = new PageInfo(page, totalPage, totalElements);
			
			List<JobResponse> list = new ArrayList<JobResponse>();
			
			if(pages.size() > 0) {
				for (Job job : pages) {
					list.add(convertJobResponse(job));
				}
			}
			
			BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
			return response;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public BaseDataMetaResponse findAllByCompanyIdAndIsDelete(int page, int paging, UUID id) throws IllegalArgumentException{
		try {
			Page<Job> pages = jobRepository.findAllByCompanyIdAndIsDelete(PageRequest.of(page-1, paging), id, false);
			PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
			
			List<JobResponse> list = new ArrayList<JobResponse>();
			
			if(pages.getContent().size() > 0) {
				for (Job job : pages.getContent()) {
					list.add(convertJobResponse(job));
				}
			}
			
			BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
			return response;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public BaseDataResponse save(JobRequest jobRequest, UserPrincipal userPrincipal) throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Job job = new Job();
			job.setName(jobRequest.getName());
			job.setCompany(companyService.findByUserAndIsDelete(userService.findById(userPrincipal.getId()), false));
			job.setBusinesses(Business.listBusiness(jobRequest.getBusinessIds()));
			job.setContract(new Contract(jobRequest.getContractId()));
			job.setLevel(new Level(jobRequest.getLevelId()));
			job.setCity(new City(jobRequest.getCityId()));
			job.setDistrict(new District(jobRequest.getDistrictId()));
			job.setAddress(jobRequest.getAddress());
			job.setDesc(jobRequest.getDesc());
			job.setRequiredEducation(jobRequest.getRequiredEducation());
			job.setRequiredExperience(jobRequest.getRequiredExperience());
			job.setRequiredLanguage(jobRequest.getRequiredLanguage());
			job.setBenefits(jobRequest.getBenefits());
			job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
			job.setApplicationDeadline(jobRequest.getApplicationDeadline());
			job.setMinSalary(jobRequest.getMinSalary());
			job.setMaxSalary(jobRequest.getMaxSalary());
			job.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);		
			job.setCreateDate(timestamp);
			job.setUpdateDate(timestamp);
			job.setDelete(false);
			
			Job result = jobRepository.save(job);
			BaseDataResponse response = new BaseDataResponse(convertJobResponse(result));		
			return response;
		}catch (Exception e) {
			throw new ServerError(MessageConstant.JOB_CREATE_FAIL);
		}
	}
	
	public BaseDataResponse update(JobRequest jobRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Job job = new Job();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
				job = jobRepository.findByIdAndIsDelete(id, false);
				if(job == null) {
					throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
				}
				
				if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			} else {
				job = jobRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			}
	
			job.setName(jobRequest.getName());
			job.setBusinesses(Business.listBusiness(jobRequest.getBusinessIds()));
			job.setContract(new Contract(jobRequest.getContractId()));
			job.setLevel(new Level(jobRequest.getLevelId()));
			job.setCity(new City(jobRequest.getCityId()));
			job.setDistrict(new District(jobRequest.getDistrictId()));
			job.setAddress(jobRequest.getAddress());
			job.setDesc(jobRequest.getDesc());
			job.setRequiredEducation(jobRequest.getRequiredEducation());
			job.setRequiredExperience(jobRequest.getRequiredExperience());
			job.setRequiredLanguage(jobRequest.getRequiredLanguage());
			job.setBenefits(jobRequest.getBenefits());
			job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
			job.setApplicationDeadline(jobRequest.getApplicationDeadline());
			job.setMinSalary(jobRequest.getMinSalary());
			job.setMaxSalary(jobRequest.getMaxSalary());
			job.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
			job.setUpdateDate(timestamp);
			
			Job result = jobRepository.save(job);		
			BaseDataResponse response = new BaseDataResponse(convertJobResponse(result));		
			return response;
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (ForbiddenException e) {
			throw e;
		}catch (Exception e) {
			throw new ServerError(MessageConstant.JOB_UPDATE_FAIL);
		}
	}
	
	public BaseDataResponse isDel(UUID id, UserPrincipal userPrincipal, boolean isDel) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Job job = jobRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
				if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			}
			
			job.setDelete(isDel);
			jobRepository.save(job);
			Job result = jobRepository.findByIdAndIsDelete(id, false);
			BaseDataResponse response = new BaseDataResponse(convertJobResponse(result));
			return response;
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (ForbiddenException e) {
			throw e;
		}catch (Exception e) {
			throw new ServerError(MessageConstant.JOB_DELETE_FAIL);
		}
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findByIdAndIsDelete(id, false);
		if(job == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}		
		
		BaseDataResponse response = new BaseDataResponse(convertJobResponse(job));
		return response;
	}
	
	private JobResponse convertJobResponse(Job job) {
		JobResponse jobResponse = new JobResponse(
				job.getId(),
				companyService.convertCompanyResponse(job.getCompany()), job.getName(), Business.listBusinessID(job.getBusinesses()), 
				job.getContract().getId(), job.getLevel().getId(), job.getJapaneseLevelRequirement(), 
				job.getRequiredEducation(), job.getRequiredExperience(), job.getRequiredLanguage(), 
				job.getDesc(), job.getCity().getId(), job.getDistrict().getId(), job.getAddress(), 
				job.getApplicationDeadline(), job.getMinSalary(), job.getMaxSalary(), job.getBenefits());
		return jobResponse;
	}
}
