package com.jobs.japan_work.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.model.UserConnection;

@Repository
@Transactional
public class UserConnectionRepository {
	
	@Autowired
    private EntityManager entityManager;
	
	public UserConnection findUserConnectionByUserProviderId(String userProviderId) {
        try {
            String sql = "FROM userconnection u WHERE u.userProviderId = :userProviderId";
  
            Query query = entityManager.createQuery(sql, Account.class);
            query.setParameter("userProviderId", userProviderId);
  
            return (UserConnection) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }   
    }
}
