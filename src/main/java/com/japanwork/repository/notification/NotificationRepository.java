package com.japanwork.repository.notification;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>{

}
