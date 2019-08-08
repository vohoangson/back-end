package com.japanwork.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.payload.response.JobApplicationStatusResponse;
import com.japanwork.repository.job_application_status.JobApplicationStatusRepository;

@Service
public class JobApplicationStatusService {
	@Autowired
	private JobApplicationStatusRepository jobApplicationStatusRepository;
	
	public JobApplicationStatus save(JobApplication jobApplication, Timestamp timestamp, String status) {
		JobApplicationStatus jobApplicationStatus = new JobApplicationStatus();
		jobApplicationStatus.setJobApplication(jobApplication);
		jobApplicationStatus.setCreatedAt(timestamp);
		jobApplicationStatus.setStatus(status);
		jobApplicationStatus.setTranslator(jobApplication.getTranslator());
		return jobApplicationStatusRepository.save(jobApplicationStatus);
	}
	
	public JobApplicationStatus save(JobApplication jobApplication, Timestamp timestamp, String status, String reason, UUID creatorId) {
		JobApplicationStatus jobApplicationStatus = new JobApplicationStatus();
		jobApplicationStatus.setJobApplication(jobApplication);
		jobApplicationStatus.setCreatorId(creatorId);
		jobApplicationStatus.setCreatedAt(timestamp);
		jobApplicationStatus.setStatus(status);
		jobApplicationStatus.setReason(reason);
		jobApplicationStatus.setTranslator(jobApplication.getTranslator());
		return jobApplicationStatusRepository.save(jobApplicationStatus);
	}
	
	public JobApplicationStatusResponse convertJobApplicationStatusResponse(JobApplicationStatus jobApplicationStatus) {
		JobApplicationStatusResponse obj = new JobApplicationStatusResponse();
		obj.setId(jobApplicationStatus.getId());
		obj.setCreatorId(jobApplicationStatus.getCreatorId());
		obj.setStatus(jobApplicationStatus.getStatus());
		obj.setReason(jobApplicationStatus.getReason());
		obj.setCreatedAt(jobApplicationStatus.getCreatedAt());
		return obj;
	}
}
