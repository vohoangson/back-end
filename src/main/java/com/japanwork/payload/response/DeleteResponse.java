package com.japanwork.payload.response;

public class DeleteResponse {
	private String delete;

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public DeleteResponse(String delete) {
		super();
		this.delete = delete;
	}
}
