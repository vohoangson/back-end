package com.japanwork.payload.response;

import java.util.HashMap;

public class BaseErrorsResponse {
    private String status;
    private HashMap<String, String> errors;

    public String getStatus() {
        return status;
    }
    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setErrors(HashMap errors) {
        this.errors = errors;
    }

    public BaseErrorsResponse(String status, HashMap errors) {
        this.status = status;
        this.errors = errors;
    }
}

