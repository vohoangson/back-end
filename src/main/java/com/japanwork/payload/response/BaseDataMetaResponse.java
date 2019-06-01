package com.japanwork.payload.response;

public class BaseDataMetaResponse {
	private Object data;
	private Object meta;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getMeta() {
		return meta;
	}
	public void setMeta(Object meta) {
		this.meta = meta;
	}
	public BaseDataMetaResponse(Object data, Object meta) {
		super();
		this.data = data;
		this.meta = meta;
	}
}
