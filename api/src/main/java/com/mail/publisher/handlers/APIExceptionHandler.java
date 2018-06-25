package com.mail.publisher.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mail.publisher.beans.EmailResponse;
import com.mail.publisher.beans.ValidationResponse;
import com.mail.publisher.exceptions.MailPublishException;

/**
 * Exception handler for API to throw back appropriate response messages
 * 
 * @author MadanMeenu
 *
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<ValidationResponse> validationFailures = fieldErrors.stream().map(error -> new ValidationResponse(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());

		return new ResponseEntity(validationFailures, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle Mail publish failure exception
	 * @param e
	 * @return response with code and message
	 */
	@ExceptionHandler(MailPublishException.class)
	public ResponseEntity<EmailResponse> handlePublishFailure(MailPublishException e) {
		EmailResponse response = new EmailResponse(e.getCode(), e.getMessage());
		
		return new ResponseEntity<EmailResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<EmailResponse> handleGenericFailure(Exception e) {
		EmailResponse response = new EmailResponse("5555", "Internal error occurred. Please try again later");
		
		return new ResponseEntity<EmailResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
