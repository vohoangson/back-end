package com.japanwork.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
	@JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    
	@JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uid;
	
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @JsonProperty("is_enabled")
    @Column(nullable = false)
    private Boolean isEnabled = false;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @JsonIgnore
    @JsonProperty("provider_id")
    private String providerId;
    
    private String role;
    
    @JsonProperty("property_id")
    @Column(name="property_id")
    private UUID propertyId;
    
    @JsonIgnore
    @Column(name="creatde_at")
    private Timestamp createdAt;
    
    @JsonIgnore
    @Column(name="updated_at")
    private Timestamp updatedAt;
    
    @JsonIgnore
    @Column(name="deleted_at")
    private Timestamp deletedAt;
    

    public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    @JsonIgnore
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    @JsonIgnore
    public String getProviderId() {
        return providerId;
    }
    
    @JsonIgnore
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public UUID getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(UUID propertyId) {
		this.propertyId = propertyId;
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

	public User(UUID uid, String name, @Email String email, Boolean isEnabled, String password,
			@NotNull AuthProvider provider, String providerId, String role) {
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.isEnabled = isEnabled;
		this.password = password;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
	}

//	public User(UUID id, String name, @Email String email, Boolean isEnabled, String password,
//			@NotNull AuthProvider provider, String providerId, String role, Timestamp createDate,
//			Timestamp updateDate, boolean isDelete) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.email = email;
//		this.isEnabled = isEnabled;
//		this.password = password;
//		this.provider = provider;
//		this.providerId = providerId;
//		this.role = role;
//		this.createDate = createDate;
//		this.updateDate = updateDate;
//		this.isDelete = isDelete;
//	}
	
	public User() {
	}

	public User(BigInteger id, UUID uid, String name, @Email String email, Boolean isEnabled, String password,
			@NotNull AuthProvider provider, String providerId, String role, UUID propertyId, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.isEnabled = isEnabled;
		this.password = password;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
		this.propertyId = propertyId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	
}
