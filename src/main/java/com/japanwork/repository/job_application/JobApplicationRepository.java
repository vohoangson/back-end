package com.japanwork.repository.job_application;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, BigInteger>{
	public JobApplication findByJobIdAndDeletedAt(UUID id, Timestamp isDelete);
	public JobApplication findByUidAndDeletedAt(UUID id, Timestamp isDelete);
}
