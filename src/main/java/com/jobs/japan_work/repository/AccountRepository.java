package com.jobs.japan_work.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.utils.EncrytedPasswordUtils;

@Repository
@Transactional
public class AccountRepository{
	@Autowired
    private EntityManager entityManager;
	
	public Account findAccountById(UUID id) {
		try {
            String sql = "Select e from " + Account.class.getName() + " e " + " Where e.id = :id";
            Query query = entityManager.createQuery(sql, Account.class);
            query.setParameter("id", id);
  
            return (Account) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Account findAccountByUserName(String username) {        
        try {
            String sql = "Select e from " + Account.class.getName() + " e " + " Where e.username = :username";
            Query query = entityManager.createQuery(sql, Account.class);
            query.setParameter("username", username);
  
            return (Account) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Account findAccountByEmail(String email) {
        try {
            String sql = "FROM account ac WHERE ac.email = :email";
  
            Query query = entityManager.createQuery(sql, Account.class);
            query.setParameter("email", email);
  
            return (Account) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	private String findAvailableUserName(String userName_prefix) {
		Account account = this.findAccountByUserName(userName_prefix);
        if (account == null) {
            return userName_prefix;
        }
        int i = 0;
        while (true) {
            String userName = userName_prefix + "_" + i++;
            account = this.findAccountByUserName(userName);
            if (account == null) {
                return userName;
            }
        }
    }
	
	public Account createAccount(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
  
        String email = userProfile.getEmail();
        Account account = this.findAccountByEmail(email);
        if (account != null) {
            return account;
        }
        String userName_prefix = userProfile.getFirstName().trim().toLowerCase()//
                + "_" + userProfile.getLastName().trim().toLowerCase();
  
        String userName = this.findAvailableUserName(userName_prefix);
        //
        // Random Password! TODO: Need send email to User!
        //
        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(randomPassword);
        //
        account = new Account();
        account.setEnabled(1);
        account.setPassword(encrytedPassword);
        account.setUsername(userName);
        account.setEmail(email);
        account.setProvider("provider");
        
        Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		account.setCreateDate(timestamp);
		account.setUpdateDate(timestamp);
		
		this.entityManager.persist(account);
  
        return account;
	}
	
	public Account registerNewAccount(Account account) {
		this.entityManager.persist(account);
		  
        return account;
	}
	
	public Account update(Account account) {
		return this.entityManager.merge(account);
	}
}
