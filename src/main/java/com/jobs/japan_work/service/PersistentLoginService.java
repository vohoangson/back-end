package com.jobs.japan_work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.PersistentLogin;
import com.jobs.japan_work.repository.PersistentLoginRepository;

@Service
public class PersistentLoginService {
	@Autowired
	PersistentLoginRepository persistentLoginRepository;
	
	public PersistentLogin findPersistentLoginByUserName(String username) {
		return persistentLoginRepository.findPersistentLoginByUserName(username);
	}
}
