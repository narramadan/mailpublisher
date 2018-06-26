package com.mail.publisher.beans;

import lombok.Data;

@Data
public class EmailResponse {

	private String code;
	private String message;
	
	public EmailResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
