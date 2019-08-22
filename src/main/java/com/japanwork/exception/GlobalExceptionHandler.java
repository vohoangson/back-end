package com.japanwork.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.payload.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException2.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException2 ex, WebRequest request) {
		ErrorResponse error = CommonFunction.getErrorFromErrors(ex.getMessage(), "errors.yml");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
		ErrorResponse error = CommonFunction.getErrorFromErrors(ex.getMessage(), "errors.yml");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbiddenException(ForbiddenException ex, WebRequest request) {
		ErrorResponse error = CommonFunction.getErrorFromErrors(ex.getMessage(), "errors.yml");
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ServerError.class)
	public ResponseEntity<?> serverError(ServerError ex, WebRequest request) {
		ErrorResponse error = CommonFunction.getErrorFromErrors(ex.getMessage(), "errors.yml");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	  MethodArgumentTypeMismatchException ex, WebRequest request) {
		String code = "invalid_" + ex.getName() + "_format";
	    String msg =
	      ex.getName() + " should be of type " + ex.getRequiredType().getName();

	    ErrorResponse error = new ErrorResponse(code, msg);
	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorResponse error = CommonFunction.getErrorFromErrors(ex.getMessage(), "errors.yml");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> listError = ex.getBindingResult().getAllErrors();
		ObjectError objectError = listError.get(listError.size()-1);
		String error = CommonFunction.convertToSnakeCase(objectError.getCode());
		String fieldName = CommonFunction.convertToSnakeCase(((FieldError) objectError).getField());
		String resource = CommonFunction.convertToSnakeCase(objectError.getObjectName());
		
		ErrorResponse errorResponse = CommonFunction.getErrorFromValidation(resource, fieldName, error, "validation.yml");
		
		ResponseDataAPI ResponseDataAPI = new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.FAILURE, 
				null, null, errorResponse);
	    return new ResponseEntity<Object>(ResponseDataAPI, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		ErrorResponse error = CommonFunction.getErrorFromErrors(MessageConstant.PAGE_NOT_FOUND, "errors.yml");
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
	  MissingServletRequestParameterException ex, HttpHeaders headers,
	  HttpStatus status, WebRequest request) {
		ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, ex.getParameterName() + " parameter is missing");
	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	  HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getMethod());
	    builder.append(
	      " method is not supported for this request. Supported methods are ");
	    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	    ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, builder.toString());
	    return new ResponseEntity<Object>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	  HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getContentType());
	    builder.append(" media type is not supported. Supported media types are ");
	    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

	    ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, builder.toString());
	    return new ResponseEntity<Object>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse error = new ErrorResponse(MessageConstant.INVALID_INPUT, ex.getMessage());
	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
