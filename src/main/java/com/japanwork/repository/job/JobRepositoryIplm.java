package com.japanwork.repository.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.model.Company;
import com.japanwork.model.Job;
import com.japanwork.payload.request.JobFilterRequest;

@Repository
@Transactional
public class JobRepositoryIplm implements JobRepository{
	@PersistenceContext 
	private EntityManager entityManager;
	
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
	public List<Job> findAllById(Iterable<UUID> ids) {
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
	public Job getOne(UUID id) {
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
	public Optional<Job> findById(UUID id) {
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
	public Job findByIdAndIsDelete(UUID id, boolean isDelete) {
		// TODO Auto-generated method stub
		return findByIdAndIsDelete(id, isDelete);
	}

	@Override
	public List<Job> findAllByIsDelete(boolean isDelete) {
		// TODO Auto-generated method stub
		return findAllByIsDelete(isDelete);
	}

	@Override
	public List<Job> findAllByCompany(Company company) {
		// TODO Auto-generated method stub
		return findAllByCompany(company);
	}

	@Override
	public Page<Job> findAllByCompanyIdAndIsDelete(Pageable page, UUID id, boolean isDelete) {
		// TODO Auto-generated method stub
		return findAllByCompanyIdAndIsDelete(page, id, isDelete);
	}

	public List<Job> filterJob(JobFilterRequest jobFilterRequest, int page, int paging, boolean isDelete) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT j FROM Job j ");
		sql.append("	INNER JOIN  j.company c ");
		sql.append("	INNER JOIN j.businesses b ");
		sql.append("	INNER JOIN j.contract con ");
		sql.append("	INNER JOIN j.level lev ");
		sql.append("	INNER JOIN j.city city ");
		sql.append("	WHERE ");
		sql.append("	j.isDelete = " + isDelete +" ");
		if(jobFilterRequest != null) {
			if(jobFilterRequest.getJobName() != null) {
				sql.append(" AND ");
				sql.append(" j.name LIKE '%" + jobFilterRequest.getJobName() + "%' ");
			}
			if(jobFilterRequest.getCompanyName() != null) {
				sql.append(" AND ");
				sql.append("c.name LIKE '%" + jobFilterRequest.getCompanyName() + "%' ");
			}
			if(!jobFilterRequest.getBusinessIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getBusinessIds());
				if(list.size() == 1) {
					sql.append("b.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( b.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR b.id = '" + list.get(i) + "' ");
					}
					sql.append(" )");
				}
			}
			
			if(!jobFilterRequest.getContractIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getContractIds());
				if(list.size() == 1) {
					sql.append("con.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( con.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR con.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			if(!jobFilterRequest.getLevelIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getLevelIds());
				if(list.size() == 1) {
					sql.append("lev.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( lev.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR lev.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			if(!jobFilterRequest.getCityIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getCityIds());
				if(list.size() == 1) {
					sql.append("city.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( city.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR city.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			
			if(jobFilterRequest.getMinSalary() > 0) {
				sql.append(" AND ");
				sql.append(" (j.minSalary > " + jobFilterRequest.getMinSalary());
				sql.append(" OR j.maxSalary > " + jobFilterRequest.getMinSalary() + ")");
			}
			
			if(jobFilterRequest.getPostTime() != null) {
				sql.append(" AND ");
				sql.append(" j.createDate >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jobFilterRequest.getPostTime()) + "'");
			}
		}
		List<Job> list = (List<Job>)entityManager.createQuery(sql.toString(), Job.class).setFirstResult((page-1)*paging)
				.setMaxResults(paging).getResultList();
		return list;
	}
	
	public long countFilterJob(JobFilterRequest jobFilterRequest, int page, int paging, boolean isDelete) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT j FROM Job j ");
		sql.append("	INNER JOIN  j.company c ");
		sql.append("	INNER JOIN j.businesses b ");
		sql.append("	INNER JOIN j.contract con ");
		sql.append("	INNER JOIN j.level lev ");
		sql.append("	INNER JOIN j.city city ");
		sql.append("	WHERE ");
		sql.append("	j.isDelete = " + isDelete +" ");
		if(jobFilterRequest != null) {
			if(jobFilterRequest.getJobName() != null) {
				sql.append(" AND ");
				sql.append(" j.name LIKE '%" + jobFilterRequest.getJobName() + "%' ");
			}
			if(jobFilterRequest.getCompanyName() != null) {
				sql.append(" AND ");
				sql.append("c.name LIKE '%" + jobFilterRequest.getCompanyName() + "%' ");
			}
			if(!jobFilterRequest.getBusinessIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getBusinessIds());
				if(list.size() == 1) {
					sql.append("b.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( b.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR b.id = '" + list.get(i) + "' ");
					}
					sql.append(" )");
				}
			}
			
			if(!jobFilterRequest.getContractIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getContractIds());
				if(list.size() == 1) {
					sql.append("con.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( con.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR con.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			if(!jobFilterRequest.getLevelIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getLevelIds());
				if(list.size() == 1) {
					sql.append("lev.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( lev.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR lev.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			if(!jobFilterRequest.getCityIds().isEmpty()) {
				sql.append(" AND ");
				List<UUID> list = new ArrayList<UUID>(jobFilterRequest.getCityIds());
				if(list.size() == 1) {
					sql.append("city.id = '" + list.get(0) + "' ");
				}
				
				if(list.size() > 1) {
					sql.append("( city.id = '" + list.get(0) + "' ");
					for(int i = 1; i< list.size(); i++) {
						sql.append(" OR city.id = '" + list.get(i) + "' ");
					}
					sql.append(" ) ");
				}
			}
			
			if(jobFilterRequest.getMinSalary() > 0) {
				sql.append(" AND ");
				sql.append(" (j.minSalary > " + jobFilterRequest.getMinSalary());
				sql.append(" OR j.maxSalary > " + jobFilterRequest.getMinSalary() + ")");
			}
			
			if(jobFilterRequest.getPostTime() != null) {
				sql.append(" AND ");
				sql.append(" j.createDate >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jobFilterRequest.getPostTime()) + "'");
			}
		}
		List<Job> list = (List<Job>)entityManager.createQuery(sql.toString(), Job.class).getResultList();
		return list.size();
	}
}
