package com.japanwork.payload.response;

public class BaseDataResponse {
	private Object data;
	public BaseDataResponse(Object data) {
		super();
		this.data = data;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
