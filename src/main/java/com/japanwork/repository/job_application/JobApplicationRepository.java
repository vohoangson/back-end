package com.japanwork.repository.job_application;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID>{
	public JobApplication findByJobIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public JobApplication findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
