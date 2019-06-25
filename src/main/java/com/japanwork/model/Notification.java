package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private long id;
	
	@OneToOne
    @JoinColumn(name = "conversation_id")
	private Conversation conversation;
	
	@Column(name = "sender_id")
	private UUID senderId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "notification_type")
	private int notificationType;
	
	@Column(name = "create_at")
	private Timestamp createAt;
	
	@Column(name = "is_delete")
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Notification(long id, Conversation conversation, UUID senderId, String content, String title,
			int notificationType, Timestamp createAt, boolean isDelete) {
		super();
		this.id = id;
		this.conversation = conversation;
		this.senderId = senderId;
		this.content = content;
		this.title = title;
		this.notificationType = notificationType;
		this.createAt = createAt;
		this.isDelete = isDelete;
	}

	public Notification() {
		super();
	}
}
