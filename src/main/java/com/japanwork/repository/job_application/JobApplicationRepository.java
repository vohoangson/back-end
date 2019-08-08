package com.japanwork.repository.job_application;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID>{
	public JobApplication findByJobAndCandidateAndDeletedAt(Job job, Candidate candidate,Timestamp deletedAt);
	public JobApplication findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public Page<JobApplication> findAllByJobCompanyIdAndDeletedAt(Pageable page, UUID companyId, Timestamp deletedAt);
	public Page<JobApplication> findAllByCandidateIdAndDeletedAt(Pageable page, UUID candidateId, Timestamp deletedAt);
}
