package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationResponse {
	private long id;
	
    @JsonProperty("conversation_id")
	private UUID conversationId;
	
    @JsonProperty("sender_id")
	private UUID senderId;
	
	private String content;
	
	private String title;
	
	@JsonProperty("notification_type")
	private int notificationType;
	
	@JsonProperty("create_at")
	private Timestamp createAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getConversationId() {
		return conversationId;
	}

	public void setConversationId(UUID conversationId) {
		this.conversationId = conversationId;
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

	public NotificationResponse(long id, UUID conversationId, UUID senderId, String content, String title,
			int notificationType, Timestamp createAt) {
		super();
		this.id = id;
		this.conversationId = conversationId;
		this.senderId = senderId;
		this.content = content;
		this.title = title;
		this.notificationType = notificationType;
		this.createAt = createAt;
	}

	public NotificationResponse() {
		super();
	}
}
