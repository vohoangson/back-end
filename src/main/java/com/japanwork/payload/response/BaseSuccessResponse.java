package com.japanwork.payload.response;

public class BaseSuccessResponse {
    private String status;
    private Object data;
    private Object meta;

    public String getStatus() {
        return status;
    }
    public Object getData() {
        return data;
    }
    public Object getMeta() {
        return meta;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public BaseSuccessResponse(String status, Object data, Object meta) {
        this.status = status;
        this.data = data;
        this.meta = meta;
    }
}
