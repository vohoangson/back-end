package com.japanwork.repository.experience;

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

import com.japanwork.model.Experience;

@Repository
@Transactional
public class ExperienceRepositoryIplm implements ExperienceRepository{

	@Override
	public List<Experience> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Experience> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Experience> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return saveAll(entities);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Experience> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Experience> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Experience getOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Experience> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Experience> findById(BigInteger id) {
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
	public void delete(Experience entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Experience> entities) {
		// TODO Auto-generated method stub
		deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Experience> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Experience> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Experience> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Experience findByUidAndDeletedAt(UUID id, Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findByUidAndDeletedAt(id, isDelete);
	}

	@Override
	public List<Experience> findByCandidateId(UUID id) {
		// TODO Auto-generated method stub
		return findByCandidateId(id);
	}
	
}
