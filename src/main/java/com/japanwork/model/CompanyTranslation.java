package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Where;

@Entity
@Table(
        name="company_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"company_id", "language_id"})}
)
@Where(clause = "deleted_at IS NULL")
public class CompanyTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

	@ManyToOne
    @JoinColumn(name = "company_id")
    @Where(clause = "deleted_at IS NULL")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "translator_id")
    @Where(clause = "deleted_at IS NULL")
    private Translator translator;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @Where(clause = "deleted_at IS NULL")
    private Language language;

	@Column(name = "name")
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public CompanyTranslation(UUID id, Company company, Translator translator, String name, String introduction,
			String address, int status, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.company = company;
		this.translator = translator;
		this.name = name;
		this.introduction = introduction;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public CompanyTranslation() {
	}
}
