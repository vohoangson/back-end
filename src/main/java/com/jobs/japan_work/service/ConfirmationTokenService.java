package com.jobs.japan_work.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.japan_work.model.ConfirmationToken;
import com.jobs.japan_work.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	public void save(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	public ConfirmationToken findById(UUID id) {
		return confirmationTokenRepository.findById(id);
	}
}
