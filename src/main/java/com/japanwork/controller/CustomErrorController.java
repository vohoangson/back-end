package com.japanwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.ErrorResponse;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseDataResponse requestHandlingNoHandlerFound() {
    	ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_404);
    	return new BaseDataResponse(error);
    }
    
    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseDataResponse unauthorized() {
    	ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_401);
    	return new BaseDataResponse(error);
    }
    
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseDataResponse forbidden() {
    	ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_401);
    	return new BaseDataResponse(error);
    }
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public BaseDataResponse badRequest() {
//    	ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_403);
//    	return new BaseDataResponse(error);
//    }
}
