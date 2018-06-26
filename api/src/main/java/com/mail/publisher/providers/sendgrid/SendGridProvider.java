package com.mail.publisher.providers.sendgrid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.publisher.beans.Email;
import com.mail.publisher.exceptions.MailPublishException;
import com.mail.publisher.http.APIHttpRequest;
import com.mail.publisher.http.APIHttpResponse;
import com.mail.publisher.http.HttpWrapper;
import com.mail.publisher.providers.MailProvider;
import com.mail.publisher.util.ResponseCodes;

/**
 * Implementation for publishing mail through SendGrid
 * @author MadanMeenu
 *
 */
@Component("sendgrid")
public class SendGridProvider implements MailProvider {

	// Constants
	private final String API_URL_MAIL_PUBLISH = "/mail/send";
	private final int MAIL_PUBLISH_RESPONSE_CODE_SUCCESS = 202;
	
	@Value("${sendgrid.api.host}")
	private String API_HOST;
	
	@Value("${sendgrid.api.key}")
	private String API_KEY;
	
	@Value("${mail.publisher.from.default}")
	private String DEFAULT_FROM_ADDRESS;
	
	@Autowired
	HttpWrapper httpWrapper;

	@Bean
	private Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("authorization", API_KEY);
		headers.put("content-type", "application/json");
		
		return headers;
	}
	
	@Override
	public void sendEmail(Email email) throws MailPublishException {
		System.out.println("Implementation for publishing mail through SendGrid");
		
		try {
			
			// Prepare Http request for publishing email
			APIHttpRequest request = new APIHttpRequest(API_HOST+ API_URL_MAIL_PUBLISH);
			request.setHeaders(getHeaders());
			
			String emailContent = prepareBodyContent(email); 
			System.out.println("=====> emailContent :: "+emailContent);
			request.setBody(emailContent);
		
			// Validate if response is successful by validating the response code. If not, throw exception
			APIHttpResponse response = httpWrapper.handlePost(request);
			if(response.getCode() != MAIL_PUBLISH_RESPONSE_CODE_SUCCESS) {
				throw new MailPublishException(ResponseCodes.FAILURE, "SendGrid email publish failed due to error code : "+response.getCode());
			}
		}
		catch(Exception e) {
			throw new MailPublishException(ResponseCodes.FAILURE, "Error occurred while sending email from SendGrid", e);
		}
	}
	
	// Create body content in json format that should be sent as body to SendGrid mail send API
	private String prepareBodyContent(Email email) throws Exception {
		
		Personalizations personalizations = new Personalizations(email.getTo().stream().map(e -> new To(e)).collect(Collectors.toList()));
		
		// Validate if there are email address for CC. If yes, add them to personalizations
		if(CollectionUtils.isNotEmpty(email.getCc())) {
			personalizations.setCc(email.getCc().stream().map(e -> new Cc(e)).collect(Collectors.toList()));
		}
		
		// Validate if there are email address for BCC. If yes, add them to personalizations
		if(CollectionUtils.isNotEmpty(email.getBcc())) {
			personalizations.setBcc(email.getBcc().stream().map(e -> new Bcc(e)).collect(Collectors.toList()));
		}
		
		// Prepare email content object with all necessary data
		EmailContent content = new EmailContent(
				Arrays.asList(personalizations), 
				email.getSubject(), 
				Arrays.asList(new Content(email.getBody(), "text/html")),
				new From(DEFAULT_FROM_ADDRESS));

		// Convert email content object to json string
		return new ObjectMapper().writeValueAsString(content);
	}
}