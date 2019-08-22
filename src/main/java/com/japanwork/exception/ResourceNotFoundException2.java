package com.japanwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException2 extends RuntimeException {
	public ResourceNotFoundException2(String message) {
    	super(message);
    }
}
