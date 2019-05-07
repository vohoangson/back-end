package com.jobs.japan_work.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="confirmation_token")
public class ConfirmationToken {
	@Id
	@Column(name="token_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID tokenId;
	@Column(name="account_id")
	private UUID accountId;
	@Column(name="create_date")
	private Timestamp createDate;
	@Column(name="is_delete")
	private int isDelete;
	public ConfirmationToken() {
		super();
	}
	public ConfirmationToken(UUID tokenId, UUID accountId, Timestamp createDate, int isDelete) {
		super();
		this.tokenId = tokenId;
		this.accountId = accountId;
		this.createDate = createDate;
		this.isDelete = isDelete;
	}
	public UUID getTokenId() {
		return tokenId;
	}
	public void setTokenId(UUID tokenId) {
		this.tokenId = tokenId;
	}
	public UUID getAccountId() {
		return accountId;
	}
	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
