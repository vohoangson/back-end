package com.japanwork.repository.request_translation;

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
import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;

@Repository
@Transactional
public class RequestTranslationRepositoryIplm implements RequestTranslationRepository{

	@Override
	public List<RequestTranslation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RequestTranslation> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RequestTranslation> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return saveAll(entities);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends RequestTranslation> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<RequestTranslation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RequestTranslation getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<RequestTranslation> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<RequestTranslation> findById(UUID id) {
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
	public void delete(RequestTranslation entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends RequestTranslation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends RequestTranslation> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends RequestTranslation> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends RequestTranslation> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestTranslation findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
			UUID ownerId, UUID objectTableId, String objectTableType, Language language,
			Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
				ownerId, objectTableId, objectTableType, language, deletedAt);
	}

	@Override
	public RequestTranslation findByIdAndDeletedAt(UUID id, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByIdAndDeletedAt(id, deletedAt);
	}

	@Override
	public Page<RequestTranslation> findAllByTranslatorAndDeletedAt(Pageable page, Translator translator, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByTranslatorAndDeletedAt(page, translator, deletedAt);
	}

	@Override
	public Page<RequestTranslation> findAllByOwnerIdAndDeletedAt(Pageable page, UUID objecttableId,
			Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findAllByOwnerIdAndDeletedAt(page, objecttableId, deletedAt);
	}

	@Override
	public RequestTranslation findByConversationAndDeletedAt(Conversation conversation, Timestamp deletedAt) {
		// TODO Auto-generated method stub
		return findByConversationAndDeletedAt( conversation, deletedAt);
	}

}
