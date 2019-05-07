package com.jobs.japan_work.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobs.japan_work.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{
	public Account findAccountByUserName(String userName);
}
