package com.japanwork.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDataAPI {
    private String status;
    private Object data;
    private Object error;
    private Object meta;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }
    
    public void setStatusSuccess() {
        this.status = "success";
    }

    public void setStatusFailure() {
        this.status = "failure";
    }

    public void savedSuccess() {
        this.status = "success";
        this.data   = "";
        this.meta   = "";
    }

	public ResponseDataAPI(String status, Object data, Object meta, Object error) {
		this.status = status;
		this.data = data;
		this.error = error;
		this.meta = meta;
	}

	public ResponseDataAPI() {
	}
}
