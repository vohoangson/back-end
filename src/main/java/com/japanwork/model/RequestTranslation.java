package com.japanwork.model;

import org.hibernate.annotations.Where;

import com.querydsl.core.annotations.QueryEntity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

@Entity
@QueryEntity
@Table(
        name="request_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"objectable_id", "language_id"})}
)
@Where(clause = "deleted_at IS NULL")
public class RequestTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "owner_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
	private UUID ownerId;

	@Column(name = "objectable_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private UUID objectableId;

    @OneToOne
    @JoinColumn(name = "translator_id")
    @Where(clause = "deleted_at IS NULL")
    private Translator translator;

    @Column(name="objectable_type", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private String objectableType;

    @Column(name="description", length = 2000)
    private String desc;

    @OneToOne
    @JoinColumn(name="conversation_id")
    @Where(clause = "deleted_at IS NULL")
    private Conversation conversation;

    @OneToOne
    @JoinColumn(name="language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @OneToMany(mappedBy = "requestTranslation", orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private Set<RequestStatus> requestStatus;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<RequestStatus> getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Set<RequestStatus> requestStatus) {
		this.requestStatus = requestStatus;
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

	public RequestTranslation(UUID id, String name, UUID ownerId, UUID objectableId, Translator translator,
			String objectableType, String desc, Conversation conversation, Language language,
			Set<RequestStatus> requestStatus, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.name = name;
		this.ownerId = ownerId;
		this.objectableId = objectableId;
		this.translator = translator;
		this.objectableType = objectableType;
		this.desc = desc;
		this.conversation = conversation;
		this.language = language;
		this.requestStatus = requestStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public RequestTranslation() {

	}
}
