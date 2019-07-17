package com.japanwork.repository.job;

import java.math.BigInteger;
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

import com.japanwork.model.Company;
import com.japanwork.model.Job;

@Repository
@Transactional
public class JobRepositoryIplm implements JobRepository{
	
	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Job> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Job> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Job getOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Job> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Job> findById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(BigInteger id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(BigInteger id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Job entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Job> entities) {
		// TODO Auto-generated method stub
		deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Job> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Job> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Job> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Job findByUidAndDeletedAt(UUID id, Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findByUidAndDeletedAt(id, isDelete);
	}

	@Override
	public List<Job> findAllByDeletedAt(Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findAllByDeletedAt(isDelete);
	}

	@Override
	public List<Job> findAllByCompany(Company company) {
		// TODO Auto-generated method stub
		return findAllByCompany(company);
	}

	@Override
	public Page<Job> findAllByCompanyUidAndDeletedAt(Pageable page, UUID id, Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findAllByCompanyUidAndDeletedAt(page, id, isDelete);
	}
}
