package com.japanwork.repository.read_notification;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.ReadNotification;

public interface ReadNotificationRepository extends JpaRepository<ReadNotification, Long>{
	public ReadNotification findByNotificationIdAndUserIdAndDeletedAt(long notificationId, UUID userId, Timestamp deletedAt);
}
