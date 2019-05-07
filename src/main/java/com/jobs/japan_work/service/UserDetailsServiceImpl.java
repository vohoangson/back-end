package com.jobs.japan_work.service;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.repository.AccountRepository;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AccountRepository accountRepository;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = this.accountRepository.findAccountByUserName(userName);
        if (account == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        
        GrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().trim());
 
        grantList.add(authority);
 
        UserDetails userDetails = (UserDetails) new User(account.getUserName(), account.getPassword().trim(), grantList);
 
        return userDetails;
    }
}