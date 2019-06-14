package com.japanwork.repository.job;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.Job;

public interface JobRepository extends JpaRepository<Job, UUID>{
	public Job findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Job> findAllByIsDelete(boolean isDelete);
	public List<Job> findAllByCompany(Company company);
	public Page<Job> findAllByIsDelete(Pageable page, boolean isDelete);
	public Page<Job> findAllByCompanyIdAndIsDelete(Pageable page, UUID id,boolean isDelete);
}
