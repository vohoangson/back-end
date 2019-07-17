package com.japanwork.repository.job;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.Job;

public interface JobRepository extends JpaRepository<Job, BigInteger>{
	public Job findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<Job> findAllByDeletedAt(Timestamp isDelete);
	public List<Job> findAllByCompany(Company company);
	public Page<Job> findAllByCompanyUidAndDeletedAt(Pageable page, UUID id, Timestamp isDelete);
}
