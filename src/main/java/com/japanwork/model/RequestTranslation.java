package com.japanwork.model;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
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
	
	@Column(name = "objectable_id")
    private UUID objectableId;
    
    @OneToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
    
    @Column(name="objectable_type")
    private String objectableType;
    
    @Column(name="description")
    private String desc;
    
    @OneToOne
    @JoinColumn(name="conversation_id")
    private Conversation conversation;
    
    @OneToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    @OneToMany
    @JoinColumn(name="objectable_id")
    @OrderBy("createdAt DESC")
    private Set<HistoryStatus> historyStatus;
    
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

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public UUID getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(UUID objectableId) {
		this.objectableId = objectableId;
	}

	public String getObjectableType() {
		return objectableType;
	}

	public void setObjectableType(String objectableType) {
		this.objectableType = objectableType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Set<HistoryStatus> getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(Set<HistoryStatus> historyStatus) {
		this.historyStatus = historyStatus;
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

	public RequestTranslation(UUID id, UUID ownerId, UUID objectableId, Translator translator, String objectableType,
			String desc, Conversation conversation, Language language, Set<HistoryStatus> historyStatus,
			Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.ownerId = ownerId;
		this.objectableId = objectableId;
		this.translator = translator;
		this.objectableType = objectableType;
		this.desc = desc;
		this.conversation = conversation;
		this.language = language;
		this.historyStatus = historyStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public RequestTranslation() {

	}
}
