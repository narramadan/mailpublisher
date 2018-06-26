package com.mail.publisher.exceptions;

/**
 * Custom exception class to hold the appropriate code and message for the cause of publish failure
 * @author MadanMeenu
 *
 */
public class MailPublishException extends Exception {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	
	public MailPublishException(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public MailPublishException(String code, String message, Throwable e) {
		super(message, e);
		
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
