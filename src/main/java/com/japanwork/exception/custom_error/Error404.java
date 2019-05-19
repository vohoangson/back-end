package com.japanwork.exception.custom_error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;

@ControllerAdvice
public class Error404 {
	@ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseDataResponse requestHandlingNoHandlerFound() {
    	BaseMessageResponse error = new BaseMessageResponse(MessageConstant.INVALID_INPUT, MessageConstant.ERROR_404);
    	return new BaseDataResponse(error);
    }
}
