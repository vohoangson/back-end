package com.japanwork.repository.job_application;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.japanwork.model.Candidate;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID>{
	public JobApplication findByJobAndCandidateAndDeletedAt(Job job, Candidate candidate,Timestamp deletedAt);
	public JobApplication findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public Page<JobApplication> findAllByJobCompanyIdAndDeletedAt(Pageable page, UUID companyId, Timestamp deletedAt);
	public Page<JobApplication> findAllByCandidateIdAndDeletedAt(Pageable page, UUID candidateId, Timestamp deletedAt);
	
    @Query("SELECT ja FROM JobApplication ja " + 
    		"INNER JOIN RequestTranslation rt " + 
    		"ON ja.id = rt.objectableId " + 
    		"AND rt.objectableType = 'REQUEST_JOB_APPLICATION_SUPPORT' " + 
    		"INNER JOIN rt.requestStatus rs "+
    		"WHERE ja.translator.id = :translatorId " + 
    		"AND ja.deletedAt is null")
	public Page<JobApplication> findAllByTranslator(Pageable page, @Param("translatorId") UUID translatorId);
}
