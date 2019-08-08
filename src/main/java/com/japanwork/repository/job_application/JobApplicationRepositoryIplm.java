package com.japanwork.repository.job_application;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.model.Candidate;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;

@Repository
@Transactional
public class JobApplicationRepositoryIplm implements JobApplicationRepository{

	@Override
	public List<JobApplication> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobApplication> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobApplication> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends JobApplication> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<JobApplication> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JobApplication getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<JobApplication> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<JobApplication> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(JobApplication entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends JobApplication> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends JobApplication> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends JobApplication> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends JobApplication> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JobApplication findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByIdAndDeletedAt(id, deletedAt);
	}

	@Override
	public JobApplication findByJobAndCandidateAndDeletedAt(Job job, Candidate candidate, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByJobAndCandidateAndDeletedAt( job, candidate, deletedAt);
	}

	@Override
	public Page<JobApplication> findAllByJobCompanyIdAndDeletedAt(Pageable page, UUID companyId, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByJobCompanyIdAndDeletedAt( page,  companyId, deletedAt);
	}

	@Override
	public Page<JobApplication> findAllByCandidateIdAndDeletedAt(Pageable page, UUID candidateId, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByCandidateIdAndDeletedAt( page, candidateId, deletedAt);
	}
}
