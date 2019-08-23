package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.File;

public class MessageResponse {
	private long id;
	
    @JsonProperty("sender_id")
	private UUID senderId;
	
	private String content;
	
	@JsonProperty("conversation_id")
	private UUID conversationId;
	
	private File file;
	
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

	public UUID getConversationId() {
		return conversationId;
	}

	public void setConversationId(UUID conversationId) {
		this.conversationId = conversationId;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@JsonIgnore
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public MessageResponse(long id, UUID senderId, String content, UUID conversationId, File file, boolean isRead,
			Timestamp createAt) {
		this.id = id;
		this.senderId = senderId;
		this.content = content;
		this.conversationId = conversationId;
		this.file = file;
		this.isRead = isRead;
		this.createAt = createAt;
	}

	public MessageResponse() {
	}

}
