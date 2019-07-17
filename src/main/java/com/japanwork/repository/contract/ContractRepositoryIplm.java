package com.japanwork.repository.contract;

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

import com.japanwork.model.Contract;

@Repository
@Transactional
public class ContractRepositoryIplm implements ContractRepository{

	@Override
	public List<Contract> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contract> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Contract> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Contract> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contract getOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Contract> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Contract> findById(BigInteger id) {
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
	public void delete(Contract entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Contract> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Contract> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Contract> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Contract> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contract findByUidAndDeletedAt(UUID id, Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findByUidAndDeletedAt(id, isDelete);
	}

	@Override
	public List<Contract> findAllByDeletedAt(Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findAllByDeletedAt(isDelete);
	}

}
