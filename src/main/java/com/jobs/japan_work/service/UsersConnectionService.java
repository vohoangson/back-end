package com.jobs.japan_work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.UserConnection;
import com.jobs.japan_work.repository.UserConnectionRepository;

@Service
public class UsersConnectionService {
	@Autowired
	private UserConnectionRepository userConnectionRepository;
	
	public UserConnection findUserConnectionByUserProviderId(String userProviderId) {
		return userConnectionRepository.findUserConnectionByUserProviderId(userProviderId);
	}
}
