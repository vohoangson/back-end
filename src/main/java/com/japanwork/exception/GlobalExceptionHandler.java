package com.japanwork.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseErrorResponse;
import com.japanwork.payload.response.BaseErrorsResponse;
import com.japanwork.payload.response.BaseMessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFound exception, WebRequest request) {
        BaseErrorsResponse error = new BaseErrorsResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.ERROR_404, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
		BaseMessageResponse error = new BaseMessageResponse(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbiddenException(ForbiddenException ex, WebRequest request) {
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.ERROR_403, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ServerError.class)
	public ResponseEntity<?> serverError(ServerError ex, WebRequest request) {
		System.out.println(ex.getMessage());
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	  MethodArgumentTypeMismatchException ex, WebRequest request) {
		String code = "invalid_" + ex.getName() + "_format";
	    String msg =
	      ex.getName() + " should be of type " + ex.getRequiredType().getName();

	    BaseErrorResponse error = new BaseErrorResponse(code, msg);
	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		System.out.println(ex.getMessage());
		BaseMessageResponse error = new BaseMessageResponse(MessageConstant.SERVER_ERROR, MessageConstant.SERVER_ERROR_MSG);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> listError = ex.getBindingResult().getAllErrors();
		String code = listError.get(listError.size()-1).getDefaultMessage();
		String msg = "";
		Properties prop = new Properties();
	    InputStream input = null;

	    try {
	        input = new FileInputStream("src/main/resources/validate.properties");

	        // load a properties file
	        prop.load(input);
	        msg = prop.getProperty(code);

            //text.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();

	    } catch (IOException e) {
	        ex.printStackTrace();
	    } finally {
	        if (input != null) {
	            try {
	                input.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		BaseErrorResponse error = new BaseErrorResponse(code, msg);

	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		BaseErrorResponse error = new BaseErrorResponse(MessageConstant.ERROR_404, MessageConstant.ERROR_404_MSG);
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
	  MissingServletRequestParameterException ex, HttpHeaders headers,
	  HttpStatus status, WebRequest request) {
	    BaseErrorResponse error = new BaseErrorResponse(MessageConstant.INVALID_INPUT, ex.getParameterName() + " parameter is missing");
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

	    BaseErrorResponse error = new BaseErrorResponse(MessageConstant.INVALID_INPUT, builder.toString());
	    return new ResponseEntity<Object>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	  HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getContentType());
	    builder.append(" media type is not supported. Supported media types are ");
	    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

	    BaseErrorResponse error = new BaseErrorResponse(MessageConstant.INVALID_INPUT, builder.toString());
	    return new ResponseEntity<Object>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		BaseErrorResponse error = new BaseErrorResponse(MessageConstant.INVALID_INPUT, ex.getMessage());
	    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
