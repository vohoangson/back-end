package com.japanwork.payload.response;

import java.util.List;

public class BaseDataMetaResponse {
	private List<Object> data;
	private Object meta;
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public Object getMeta() {
		return meta;
	}
	public void setMeta(Object meta) {
		this.meta = meta;
	}
	public BaseDataMetaResponse(List<Object> data, Object meta) {
		super();
		this.data = data;
		this.meta = meta;
	}
}
