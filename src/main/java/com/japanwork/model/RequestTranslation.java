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
@Table(name="request_translation")
public class RequestTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
	@Column(name = "owner_id")
	private UUID ownerId;
	
	@Column(name = "objecttable_id")
    private UUID objectTableId;
    
    @OneToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
    
    @Column(name="objecttable_type")
    private String objectTableType;
    
    @Column(name="description")
    private String desc;
    
    @OneToOne
    @JoinColumn(name="conversation_id")
    private Conversation converstaion;
    
    @OneToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    @Column(name="created_at")
    private Timestamp createdAt;
    
    @Column(name="updated_at")
    private Timestamp updatedAt;
    
    @Column(name="deleted_at")
    private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public UUID getObjectTableId() {
		return objectTableId;
	}

	public void setObjectTableId(UUID objectTableId) {
		this.objectTableId = objectTableId;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public String getObjectTableType() {
		return objectTableType;
	}

	public void setObjectTableType(String objectTableType) {
		this.objectTableType = objectTableType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Conversation getConverstaion() {
		return converstaion;
	}

	public void setConverstaion(Conversation converstaion) {
		this.converstaion = converstaion;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

	public RequestTranslation(UUID id, UUID ownerId, UUID objectTableId, Translator translator, String objectTableType,
			String desc, Conversation converstaion, Language language, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt) {
		this.id = id;
		this.ownerId = ownerId;
		this.objectTableId = objectTableId;
		this.translator = translator;
		this.objectTableType = objectTableType;
		this.desc = desc;
		this.converstaion = converstaion;
		this.language = language;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public RequestTranslation() {

	}
}
