package com.mail.publisher.beans;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Email {

	@NotEmpty(message = "To field should have atleast one email address provided")
	private List<String> to;
	
	private List<String> cc;
	private List<String> bcc;
	
	@NotBlank(message = "Subject shouldn't be empty") 
	private String subject;
	
	@NotBlank(message = "Email body shouldn't be empty") 
	private String body;
}
