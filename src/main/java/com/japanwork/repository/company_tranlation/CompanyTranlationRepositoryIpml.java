package com.japanwork.repository.company_tranlation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.japanwork.model.CompanyTranslation;

public class CompanyTranlationRepositoryIpml implements CompanyTranlationRepository{

	@Override
	public List<CompanyTranslation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyTranslation> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyTranslation> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends CompanyTranslation> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<CompanyTranslation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CompanyTranslation getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CompanyTranslation> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<CompanyTranslation> findById(UUID id) {
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
	public void delete(CompanyTranslation entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends CompanyTranslation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends CompanyTranslation> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CompanyTranslation> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends CompanyTranslation> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CompanyTranslation findByIdAndIsDelete(UUID id, boolean isDelete) {
		// TODO Auto-generated method stub
		return findByIdAndIsDelete(id, isDelete);
	}

}
