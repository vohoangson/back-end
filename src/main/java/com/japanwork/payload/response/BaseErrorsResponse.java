package com.japanwork.payload.response;

import java.util.HashMap;

public class BaseErrorsResponse {
    private String status;
    private HashMap<String, HashMap> errors;

    public String getStatus() {
        return status;
    }
    public HashMap<String, HashMap> getErrors() {
        return errors;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setErrors(HashMap errors) {
        this.errors = errors;
    }

    public BaseErrorsResponse(Integer code, String message) {
        this.status = "error";
        this.errors = setErrorBody(code, message);
    }

    private HashMap setErrorBody(Integer code, String message) {
        HashMap<String, Object> errorBody = new HashMap<>();

        errorBody.put("code", code);
        errorBody.put("message", message);

        return errorBody;
    }
}

