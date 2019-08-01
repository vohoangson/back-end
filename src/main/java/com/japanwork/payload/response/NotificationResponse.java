package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationResponse {
	private long id;
	
    @JsonProperty("objectable_id")
	private UUID objectableId;
    
    @JsonProperty("notification_type")
	private String notificationType;
    
    @JsonProperty("receiver_id")
	private UUID receiverId;
	
    @JsonProperty("sender_id")
	private UUID senderId;
	
	private String content;
	
	@JsonProperty("sub_objectable_id")
	private UUID subObjectableId;
	
	@JsonProperty("is_read")
	private boolean isRead;
	
	@JsonProperty("create_at")
	private Timestamp createAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(UUID objectableId) {
		this.objectableId = objectableId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public UUID getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(UUID receiverId) {
		this.receiverId = receiverId;
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

	public UUID getSubObjectableId() {
		return subObjectableId;
	}

	public void setSubObjectableId(UUID subObjectableId) {
		this.subObjectableId = subObjectableId;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public NotificationResponse(long id, UUID objectableId, String notificationType, UUID receiverId, UUID senderId,
			String content, UUID subObjectableId, boolean isRead, Timestamp createAt) {
		this.id = id;
		this.objectableId = objectableId;
		this.notificationType = notificationType;
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.content = content;
		this.subObjectableId = subObjectableId;
		this.isRead = isRead;
		this.createAt = createAt;
	}

	public NotificationResponse() {
	}

}
