package com.japanwork.repository.conversation;

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

import com.japanwork.model.Conversation;

@Repository
@Transactional
public class ConversationRepositoryIplm implements ConversationRepository{

	@Override
	public List<Conversation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Conversation> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Conversation> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Conversation> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Conversation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Conversation getOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Conversation> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Conversation> findById(BigInteger id) {
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
	public void delete(Conversation entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Conversation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Conversation> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Conversation> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Conversation> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Conversation findByUidAndDeletedAt(UUID id, Timestamp isDelete) {
		// TODO Auto-generated method stub
		return findByUidAndDeletedAt(id, isDelete);
	}

}
