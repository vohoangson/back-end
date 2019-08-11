package com.japanwork.repository.job_application_status;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.japanwork.model.JobApplicationStatus;

public interface JobApplicationStatusRepository extends JpaRepository<JobApplicationStatus, UUID>{
	public List<JobApplicationStatus> findByJobApplicationIdOrderByCreatedAtDesc(UUID jobApplicationId);
	
	@Query("SELECT ja FROM JobApplicationStatus ja "
			+ "WHERE (ja.jobApplication, ja.translator, ja.createdAt ) IN "
			+ "( 	SELECT jas.jobApplication, jas.translator, max(jas.createdAt) AS createdAt "
			+ "		FROM JobApplicationStatus jas "
			+ "		WHERE jas.translator.id = :translatorId "
			+ "		GROUP BY ( jas.jobApplication, jas.translator ))"
			+ "ORDER BY ja.createdAt DESC")
	public Page<JobApplicationStatus> findByTranslator(Pageable pageable, @Param("translatorId") UUID transaltorId);
}
