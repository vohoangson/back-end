package com.jobs.japan_work.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.repository.AccountRepository;
 
public class ConnectionSignUpImpl implements ConnectionSignUp {
 
    private AccountRepository accountRepository;
 
    public ConnectionSignUpImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
 
    // After logging in social networking.
    // This method will be called to create a corresponding App_User record
    // if it does not already exist.
    @Override
    public String execute(Connection<?> connection) {
 
    	Account account = accountRepository.createAccount(connection);
        return account.getUsername();
    }
 
}
