package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.JobApplication;
import com.japanwork.repository.job_application.JobApplicationRepository;

@Service
public class JobApplicationService {
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	public JobApplication findByJobIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByJobIdAndIsDelete(id, false);
	}
	
	public JobApplication findByIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByIdAndIsDelete(id, false);
	}
	
	public JobApplication save(JobApplication jobApplication) {
		return jobApplicationRepository.save(jobApplication);
	}
}
