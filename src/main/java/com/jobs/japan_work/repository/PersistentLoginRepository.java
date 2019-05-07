package com.jobs.japan_work.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jobs.japan_work.model.PersistentLogin;

@Repository
public class PersistentLoginRepository {
	@Autowired
    private EntityManager entityManager;
	
	public PersistentLogin findPersistentLoginByUserName(String username) {
		try {
            String sql = "Select e from " + PersistentLogin.class.getName() + " e " + " Where e.Username = :username ORDER BY last_used DESC LIMIT 1 OFFSET 1";
            Query query = entityManager.createQuery(sql, PersistentLogin.class);
            query.setParameter("id", username);
  
            return (PersistentLogin) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
}
