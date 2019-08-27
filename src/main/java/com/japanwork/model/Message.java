package com.japanwork.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "message")
@Where(clause = "deleted_at IS NULL")
public class Message {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
	private User sender;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "conversation_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
	private Conversation conversation;

	@Column(name = "is_read")
	private boolean isRead;

	@OneToOne(mappedBy = "message")
    @OrderBy("createdAt DESC")
    private File file;
	
    @Column(name="created_at")
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name="updated_at")
    private Timestamp updatedAt;

    @JsonIgnore
    @Column(name="deleted_at")
    private Timestamp deletedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Message() {
	}

	public Message(long id, User sender, String content, Conversation conversation, boolean isRead,
			File file, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.sender = sender;
		this.content = content;
		this.conversation = conversation;
		this.isRead = isRead;
		this.file = file;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

}
