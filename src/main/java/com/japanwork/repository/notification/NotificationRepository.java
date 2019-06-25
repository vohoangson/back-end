package com.japanwork.repository.notification;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>{
	public Page<Notification> findByConversationIdAndIsDelete(Pageable page, UUID id, boolean isDelete);
}
