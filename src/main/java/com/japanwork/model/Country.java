package com.japanwork.model;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="code", nullable = false, unique = true)
    private String code;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @JsonIgnore
    private Timestamp createdAt;
    
    @JsonIgnore
    private Timestamp updatedAt;
    
    @JsonIgnore
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Country(UUID id, String name, String code, Language language, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.language = language;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Country(UUID id) {
        this.id = id;
    }

    public Country() {
    }
}
