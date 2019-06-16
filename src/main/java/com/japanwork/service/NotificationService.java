package com.japanwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.notification.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	
	public BaseDataResponse save() {
		return new BaseDataResponse("");
	}
}
