package com.japanwork.repository.company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.japanwork.model.Company;

@Repository
public class CompanyRepositoryIplm implements CompanyRepository{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Company> findAll() {
		// TODO Auto-generated method stub
		return findAll();
	}

	@Override
	public List<Company> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> findAllById(Iterable<UUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Company> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Company> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Company getOne(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Company> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<Company> findById(UUID id) {
		// TODO Auto-generated method stub
		return findById(id);
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
	public void delete(Company entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Company> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Company> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Company> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Company> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Company findByIdAndIsDelete(UUID id, boolean isDelete) {
//		String sql = "Select new " + Company.class.getName()
//                + "(c.id,c.userId,c.name,c.bussinessTypeId,c.scale,c.districtId,c.address,c.logoUrl,"
//                + "c.coverImageUrl,c.introduction,c.isPublised,c.createDate,c.updateDate,c.isDelete,c.cityId"
//                + " from " + Company.class.getName() + " c "
//				+ " where c.id = :id and c.isDelete = false";
//		
//		Query query = entityManager.createQuery(sql, Company.class);
//        query.setParameter("id", id);
//		String hql = "FROM company as c WHERE c.id = ? and c.is_delete = fasle";
//		Company company = (Company) entityManager.createQuery(hql).setParameter(1, id).getResultList().get(0);
		return findByIdAndIsDelete(id, isDelete) ;
	}
	
	
}
