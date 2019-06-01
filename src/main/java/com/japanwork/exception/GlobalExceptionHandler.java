package com.japanwork.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseErrorResponse;
import com.japanwork.payload.response.BaseMessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.INVALID_INPUT, ex.getMessage());
		BaseDataResponse baseDataResponse = new BaseDataResponse(error);
		return new ResponseEntity<>(baseDataResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.INVALID_INPUT, ex.getMessage());
		BaseDataResponse baseDataResponse = new BaseDataResponse(error);
		return new ResponseEntity<>(baseDataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	      HttpHeaders headers, HttpStatus status, WebRequest request) {
		String er = "";
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			er += error.getDefaultMessage() + ",";
		}
		er = "{" + er.substring(0, er.length()-1) + "}";
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(er).getAsJsonObject();
		Object object = gson.fromJson(jsonObject, Object.class);
		BaseErrorResponse error = new BaseErrorResponse(MessageConstant.INVALID_INPUT, object);
		BaseDataResponse baseDataResponse = new BaseDataResponse(error);
	    return new ResponseEntity(baseDataResponse, HttpStatus.BAD_REQUEST);
	  } 
}
