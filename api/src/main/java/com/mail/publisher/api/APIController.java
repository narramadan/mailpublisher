package com.mail.publisher.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mail.publisher.beans.Email;
import com.mail.publisher.beans.EmailResponse;
import com.mail.publisher.exceptions.MailPublishException;
import com.mail.publisher.providers.MailPublisher;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	private MailPublisher mailPublisher;
	
	@RequestMapping("/publish")
	public ResponseEntity<EmailResponse> publishEmail(@Valid @RequestBody Email email) throws MailPublishException, Exception {
		
		mailPublisher.publishEmail(email);
		
		EmailResponse response = new EmailResponse("5000", "Mail published successfully");
	
		return new ResponseEntity<EmailResponse>(response, HttpStatus.OK);
	}
}