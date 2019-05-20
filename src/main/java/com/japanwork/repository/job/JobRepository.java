package com.japanwork.repository.job;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Job;

public interface JobRepository extends JpaRepository<Job, UUID>{
	public Job findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Job> findAllByIsDelete(boolean isDelete);
}
