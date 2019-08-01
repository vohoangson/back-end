package com.japanwork.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.ReadNotification;
import com.japanwork.repository.read_notification.ReadNotificationRepository;

@Service
public class ReadNotificationService {
	@Autowired
	private ReadNotificationRepository readNotificationRepository;
	
	public ReadNotification readNotificationByNotificationIdAndUserIdAndDeletedAt(long notificationId, UUID userId, Timestamp deletedAt) {
		return readNotificationRepository.findByNotificationIdAndUserIdAndDeletedAt( notificationId, userId, deletedAt);
	}
}
