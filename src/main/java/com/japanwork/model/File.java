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

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="file")
public class File {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	@JsonIgnore
	private UUID id;

	@OneToOne
	@JoinColumn(name = "message_id", unique = true, nullable = false)
    @Where(clause = "deleted_at IS NULL")
	@JsonIgnore
	private Message message;

	@Column(name = "name", length = 1000)
	private String name;

	@Column(name = "url")
	private String url;

	@JsonIgnore
	@Column(name = "created_at")
	private Timestamp createdAt;

	@JsonIgnore
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@JsonIgnore
	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public File() {
	}
	
	public File(UUID id, Message message, String name, String url, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt) {
		this.id = id;
		this.message = message;
		this.name = name;
		this.url = url;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
}
