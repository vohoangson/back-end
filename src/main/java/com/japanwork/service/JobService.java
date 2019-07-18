package com.japanwork.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class JobService {
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	public BaseDataMetaResponse findAllByIsDelete(JobFilterRequest jobFilterRequest, int page, int paging) 
			throws IllegalArgumentException{
		try {	
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT j ");
			sql.append("    FROM Job j ");
			sql.append("	INNER JOIN  j.company c ");
			sql.append("	INNER JOIN j.businesses b ");
			sql.append("	INNER JOIN j.contract con ");
			sql.append("	INNER JOIN j.level lev ");
			sql.append("	INNER JOIN j.city city ");			
			sql.append("	WHERE ");
			sql.append("	j.deletedAt is null ");
			if(jobFilterRequest != null) {
				if(!jobFilterRequest.getJobName().isEmpty()) {
					sql.append(" AND ");
					sql.append(" j.name LIKE '%" + jobFilterRequest.getJobName() + "%' ");
				}
				if(!jobFilterRequest.getCompanyName().isEmpty()) {
					sql.append(" AND ");
					sql.append("c.name LIKE '%" + jobFilterRequest.getCompanyName() + "%' ");
				}
				if(jobFilterRequest.getBusinessIds() != null) {
					sql.append(" AND ");
					if(jobFilterRequest.getBusinessIds().size() == 1) {
						sql.append("b.id = '" + jobFilterRequest.getBusinessIds().get(0) + "' ");
					}
					
					if(jobFilterRequest.getBusinessIds().size() > 1) {
						sql.append("( b.id = '" + jobFilterRequest.getBusinessIds().get(0) + "' ");
						for(int i = 1; i< jobFilterRequest.getBusinessIds().size(); i++) {
							sql.append(" OR b.id = '" + jobFilterRequest.getBusinessIds().get(i) + "' ");
						}
						sql.append(" )");
					}
				}
				
				if(jobFilterRequest.getContractIds() != null) {
					sql.append(" AND ");
					if(jobFilterRequest.getContractIds().size() == 1) {
						sql.append("con.id = '" + jobFilterRequest.getContractIds().get(0) + "' ");
					}
					
					if(jobFilterRequest.getContractIds().size() > 1) {
						sql.append("( con.id = '" + jobFilterRequest.getContractIds().get(0) + "' ");
						for(int i = 1; i< jobFilterRequest.getContractIds().size(); i++) {
							sql.append(" OR con.id = '" + jobFilterRequest.getContractIds().get(i) + "' ");
						}
						sql.append(" ) ");
					}
				}
				if(jobFilterRequest.getLevelIds() != null) {
					sql.append(" AND ");
					if(jobFilterRequest.getLevelIds().size() == 1) {
						sql.append("lev.id = '" + jobFilterRequest.getLevelIds().get(0) + "' ");
					}
					
					if(jobFilterRequest.getLevelIds().size() > 1) {
						sql.append("( lev.id = '" + jobFilterRequest.getLevelIds().get(0) + "' ");
						for(int i = 1; i< jobFilterRequest.getLevelIds().size(); i++) {
							sql.append(" OR lev.id = '" + jobFilterRequest.getLevelIds().get(i) + "' ");
						}
						sql.append(" ) ");
					}
				}
				if(jobFilterRequest.getCityIds() != null) {
					sql.append(" AND ");
					if(jobFilterRequest.getCityIds().size() == 1) {
						sql.append("city.id = '" + jobFilterRequest.getCityIds().get(0) + "' ");
					}
					
					if(jobFilterRequest.getCityIds().size() > 1) {
						sql.append("( city.id = '" + jobFilterRequest.getCityIds().get(0) + "' ");
						for(int i = 1; i< jobFilterRequest.getCityIds().size(); i++) {
							sql.append(" OR city.id = '" + jobFilterRequest.getCityIds().get(i) + "' ");
						}
						sql.append(" ) ");
					}
				}
				
				if(jobFilterRequest.getMinSalary() != 0) {
					sql.append(" AND ");
					sql.append(" (j.minSalary > " + jobFilterRequest.getMinSalary());
					sql.append(" OR j.maxSalary > " + jobFilterRequest.getMinSalary() + ")");
				}
				
				if(!jobFilterRequest.getPostTime().isEmpty()) {
					sql.append(" AND ");
					try {
						sql.append(" j.createdAt >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jobFilterRequest.getPostTime()) + "'");
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				sql.append(" ORDER BY j.createdAt ASC ");
			}
			
			List<Job> pages = (List<Job>)entityManager.createQuery(sql.toString(), Job.class).setFirstResult((page-1)*paging)
					.setMaxResults(paging).getResultList();
			
			long totalElements = ((List<Job>)entityManager.createQuery(sql.toString(), Job.class).getResultList()).size();
			
			int totalPage = (int)totalElements / paging;
			if((totalElements % paging) > 0) {
				totalPage ++;
			}
			if(totalPage == 0) {
				totalPage = 1;
			}
			
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
	
	public Page<Job> findAllByCompanyIdAndIsDelete(int page, int paging, UUID id) throws IllegalArgumentException{
		try {
			Page<Job> pages = jobRepository.findAllByCompanyIdAndDeletedAt(PageRequest.of(page-1, paging), id, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Job save(JobRequest jobRequest, UserPrincipal userPrincipal) throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Job job = new Job();
			job.setName(jobRequest.getName());
			job.setCompany(companyService.findByUserAndIsDelete(userService.findById(userPrincipal.getId()), false));
			job.setBusinesses(new Business(jobRequest.getBusinessId()));
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
			job.setCreatedAt(timestamp);
			job.setUpdatedAt(timestamp);
			job.setDeletedAt(null);
			
			Job result = jobRepository.save(job);		
			return result;
		}catch (Exception e) {
			throw new ServerError(MessageConstant.JOB_CREATE_FAIL);
		}
	}
	
	public Job update(JobRequest jobRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Job job = new Job();
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
				job = jobRepository.findByIdAndDeletedAt(id, null);
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
			job.setBusinesses(new Business(jobRequest.getBusinessId()));
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
			job.setUpdatedAt(timestamp);
			
			Job result = jobRepository.save(job);		
			return result;
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (ForbiddenException e) {
			throw e;
		}catch (Exception e) {
			throw new ServerError(MessageConstant.JOB_UPDATE_FAIL);
		}
	}
	
	public Job isDel(UUID id, UserPrincipal userPrincipal, Timestamp deletedAt) 
			throws ResourceNotFoundException, ForbiddenException, ServerError{
		try {
			Job job = jobRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
			
			if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_COMPANY")) {
				if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
					throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
				}
			}
			
			job.setDeletedAt(deletedAt);
			jobRepository.save(job);
			Job result = jobRepository.findByIdAndDeletedAt(id, null);
			return result;
		}catch (ResourceNotFoundException e) {
			throw e;
		}catch (ForbiddenException e) {
			throw e;
		}catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.JOB_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.JOB_UN_DELETE_FAIL);
			}
			
		}
	}
	
	public Job findByIdAndIsDelete(UUID id) throws ResourceNotFoundException{
		Job job = jobRepository.findByIdAndDeletedAt(id, null);
		if(job == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}	
		return job;
	}
	
	public JobResponse convertJobResponse(Job job) {
		JobResponse jobResponse = new JobResponse(
				job.getId(),
				companyService.convertCompanyResponse(job.getCompany()), job.getName(), job.getBusinesses().getId(), 
				job.getContract().getId(), job.getLevel().getId(), job.getJapaneseLevelRequirement(), 
				job.getRequiredEducation(), job.getRequiredExperience(), job.getRequiredLanguage(), 
				job.getDesc(), job.getCity().getId(), job.getDistrict().getId(), job.getAddress(), 
				job.getApplicationDeadline(), job.getMinSalary(), job.getMaxSalary(), job.getBenefits(), job.getCreatedAt());
		return jobResponse;
	}
}
