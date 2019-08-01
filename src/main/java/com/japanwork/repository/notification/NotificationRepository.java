package com.japanwork.repository.notification;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>{
	public Page<Notification> findByObjectableIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt);
	public Page<Notification> findByObjectableIdInAndDeletedAtOrReceiverIdAndDeletedAt(Pageable page, Set<UUID> conversationIds, 
			Timestamp deletedAt, UUID id, Timestamp deletedAt1);
}
