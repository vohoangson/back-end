package com.jobs.japan_work.repository;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobs.japan_work.model.ConfirmationToken;

@Repository
@Transactional
public class ConfirmationTokenRepository{

	@Autowired
    private EntityManager entityManager;
	
	public void save(ConfirmationToken confirmationToken) {
		this.entityManager.persist(confirmationToken);
	}
	
	public ConfirmationToken findById(UUID id) {
		try {
            String sql = "Select e from " + ConfirmationToken.class.getName() + " e " + " Where e.id = :id";
            Query query = entityManager.createQuery(sql, ConfirmationToken.class);
            query.setParameter("id", id);
  
            return (ConfirmationToken) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
}
