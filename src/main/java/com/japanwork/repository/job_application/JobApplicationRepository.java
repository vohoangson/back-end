package com.japanwork.repository.job_application;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID>{
	public JobApplication findByJobIdAndIsDelete(UUID id, boolean isDelete);
	public JobApplication findByIdAndIsDelete(UUID id, boolean isDelete);
}
