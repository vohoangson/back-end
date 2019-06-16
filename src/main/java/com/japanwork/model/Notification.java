package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "notification")
public class Notification {

	@Id
    @Column(name="id")
	private long id;
	
	@OneToOne
    @JoinColumn(name = "conversation_id")
	private Conversation conversation;
	
	@JsonProperty("sender_id")
	private UUID senderId;
	
	private String title;
	
	@JsonProperty("notification_type")
	private int notificationType;
	
	@JsonIgnore
	private Timestamp createAt;
	
	@JsonIgnore
	private boolean isDelete;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public UUID getSenderId() {
		return senderId;
	}

	public void setSenderId(UUID senderId) {
		this.senderId = senderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	@JsonIgnore
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Notification(long id, Conversation conversation, UUID senderId, String title, int notificationType,
			Timestamp createAt, boolean isDelete) {
		super();
		this.id = id;
		this.conversation = conversation;
		this.senderId = senderId;
		this.title = title;
		this.notificationType = notificationType;
		this.createAt = createAt;
		this.isDelete = isDelete;
	}

	public Notification() {
		super();
	}
}
