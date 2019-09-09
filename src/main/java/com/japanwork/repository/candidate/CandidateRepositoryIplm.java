package com.japanwork.repository.candidate;

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
import com.japanwork.model.User;

@Repository
@Transactional
public class CandidateRepositoryIplm implements CandidateRepository{

	@Override
	public List<Candidate> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Candidate> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Candidate> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Candidate> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Candidate> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public Candidate getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Candidate> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Candidate> findById(UUID id) {
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
	public void delete(Candidate entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Candidate> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Candidate> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Candidate> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Candidate> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Candidate findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByIdAndDeletedAt(id, deletedAt);
	}

	@Override
	public List<Candidate> findAllByDeletedAt(Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByDeletedAt(deletedAt);
	}

	@Override
	public Candidate findByUser(User user) {
		// TODO Auto-generated method stub
		return findByUser(user);
	}

	@Override
	public Page<Candidate> findAllByDeletedAt(Pageable page, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByDeletedAt(page, deletedAt);
	}

	@Override
	public Candidate findByUserIdAndDeletedAt(UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByUserIdAndDeletedAt(id, deletedAt);
	}

	@Override
	public Page<Candidate> findAllByIdInAndDeletedAt(Pageable page, Iterable<UUID> ids, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByIdInAndDeletedAt(page, ids, deletedAt);
	}

}
