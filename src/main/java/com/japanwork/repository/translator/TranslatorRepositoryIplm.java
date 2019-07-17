package com.japanwork.repository.translator;

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

import com.japanwork.model.Translator;
import com.japanwork.model.User;

@Repository
@Transactional
public class TranslatorRepositoryIplm implements TranslatorRepository{

	@Override
	public List<Translator> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Translator> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Translator> findAllById(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Translator> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Translator> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Translator getOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Translator> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Translator> findById(BigInteger id) {
		// TODO Auto-generated method stub
		return findById(id);
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
	public void delete(Translator entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Translator> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Translator> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Translator> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Translator> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Translator findByUser(User user) {
		// TODO Auto-generated method stub
		return findByUser(user);
	}

	@Override
	public Translator findByUserAndDeletedAt(User user, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByUserAndDeletedAt(user, deletedAt);
	}

	@Override
	public Translator findByUidAndDeletedAt(UUID uid, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByUidAndDeletedAt(uid, deletedAt);
	}

	@Override
	public Translator findByUid(UUID uid) {
		// TODO Auto-generated method stub
		return findByUid(uid);
	}

	@Override
	public Page<Translator> findAllByDeletedAt(Pageable page, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByDeletedAt(page, deletedAt);
	}

}
