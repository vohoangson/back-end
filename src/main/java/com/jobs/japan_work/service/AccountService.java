package com.jobs.japan_work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository; 
	
	public List<Account> findAll(){
		return accountRepository.findAll();
	}
	public Account save(Account account){
		return accountRepository.save(account);
	}
}
