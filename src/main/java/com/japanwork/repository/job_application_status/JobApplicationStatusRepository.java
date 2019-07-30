package com.japanwork.repository.job_application_status;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.JobApplicationStatus;

public interface JobApplicationStatusRepository extends JpaRepository<JobApplicationStatus, UUID>{
	public List<JobApplicationStatus> findByJobApplicationIdOrderByCreatedAtDesc(UUID jobApplicationId);
}
