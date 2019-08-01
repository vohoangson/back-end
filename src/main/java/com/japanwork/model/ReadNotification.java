package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "read_notification")
public class ReadNotification {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private long id;
	
	@Column(name = "notification_id")
	private long notificationId;
	
	@Column(name = "user_id")
	private UUID userId;
	
	@Column(name="read_at")
    private Timestamp readAt;;
    
    @Column(name="deleted_at")
    private Timestamp deletedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Timestamp getReadAt() {
		return readAt;
	}

	public void setReadAt(Timestamp readAt) {
		this.readAt = readAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public ReadNotification(long id, long notificationId, UUID userId, Timestamp readAt, Timestamp deletedAt) {
		this.id = id;
		this.notificationId = notificationId;
		this.userId = userId;
		this.readAt = readAt;
		this.deletedAt = deletedAt;
	}

	public ReadNotification() {
	}
}
