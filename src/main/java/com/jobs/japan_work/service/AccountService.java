package com.jobs.japan_work.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.model.form.AccountForm;
import com.jobs.japan_work.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository; 
	
	public Account registerNewAccount(AccountForm accountForm, String role){
		Account account = new Account();
		
		account.setEmail(accountForm.getEmail());
		account.setUsername(accountForm.getUserName());
		account.setProvider(accountForm.getSignInProvider());
		account.setRole(role);
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		account.setPassword(bCryptPasswordEncoder.encode(accountForm.getPassword()));
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		account.setCreateDate(timestamp);
		account.setUpdateDate(timestamp);
		account.setEnabled(1);
		
		return accountRepository.registerNewAccount(account);
	}
	
	public Account registerNewAccount(Account account){
		return accountRepository.registerNewAccount(account);
	}
	
	public Account findAccountByEmail(String email) {
		return accountRepository.findAccountByEmail(email);
	}
	
	public Account findAccountById(UUID id) {
		return accountRepository.findAccountById(id);
	}
	
	public Account update(Account account) {
		return accountRepository.update(account);
	}
}
