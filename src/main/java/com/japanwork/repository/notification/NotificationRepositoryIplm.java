package com.japanwork.repository.notification;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.model.Notification;

@Repository
@Transactional
public class NotificationRepositoryIplm implements NotificationRepository{

	@Override
	public List<Notification> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notification> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notification> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Notification> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Notification> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Notification getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Notification> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Notification> findById(UUID id) {
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
	public void delete(Notification entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Notification> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Notification> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Notification> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Notification> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Notification> findByObjectableIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByObjectableIdAndDeletedAt(page, id, deletedAt);
	}

	@Override
	public Page<Notification> findByReceiverIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByReceiverIdAndDeletedAt( page, id, deletedAt);
	}

	@Override
	public int countByReceiverIdAndIsReadAndDeletedAt(UUID id, boolean isRead, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return countByReceiverIdAndIsReadAndDeletedAt( id, isRead, deletedAt);
	}

	@Override
	public void updateIsReadByIdInAndDeletedAt(boolean isRead, Set<Long> ids) {
		// TODO Auto-generated method stub
		updateIsReadByIdInAndDeletedAt( isRead, ids);
	}

	@Override
	public void updateIsReadByReceiverIdAndDeletedAt(boolean isRead, UUID receiverId) {
		// TODO Auto-generated method stub
		updateIsReadByReceiverIdAndDeletedAt( isRead, receiverId);
	}
}
