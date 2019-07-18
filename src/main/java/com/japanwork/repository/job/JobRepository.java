package com.japanwork.repository.job;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.Job;

public interface JobRepository extends JpaRepository<Job, UUID>{
	public Job findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Job> findAllByDeletedAt(Timestamp deletedAt);
	public List<Job> findAllByCompany(Company company);
	public Page<Job> findAllByCompanyIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt);
}
