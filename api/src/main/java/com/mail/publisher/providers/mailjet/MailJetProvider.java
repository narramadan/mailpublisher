package com.mail.publisher.providers.mailjet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.publisher.beans.Email;
import com.mail.publisher.exceptions.MailPublishException;
import com.mail.publisher.http.APIHttpRequest;
import com.mail.publisher.http.APIHttpResponse;
import com.mail.publisher.http.Authentication;
import com.mail.publisher.http.HttpWrapper;
import com.mail.publisher.providers.MailProvider;
import com.mail.publisher.util.ResponseCodes;

/**
 * Implementation for publishing mail through Mail Gun
 * 
 * @author MadanMeenu
 *
 */
@Component("mailjet")
public class MailJetProvider implements MailProvider {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// Constants
	private final String API_URL_MAIL_PUBLISH = "/send";
	private final int MAIL_PUBLISH_RESPONSE_CODE_SUCCESS = 200;

	@Value("${mailjet.api.host}")
	private String API_HOST;

	@Value("${mailjet.api.key.public}")
	private String API_KEY_PUBLIC;

	@Value("${mailjet.api.key.private}")
	private String API_KEY_PRIVATE;

	@Value("${mail.publisher.from.default}")
	private String DEFAULT_FROM_ADDRESS;

	@Autowired
	HttpWrapper httpWrapper;

	@Bean
	private Authentication getAuthentication() {
		return new Authentication(API_KEY_PUBLIC, API_KEY_PRIVATE);
	}

	@Bean
	private Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("content-type", "application/json");

		return headers;
	}

	@Override
	public void sendEmail(Email email) throws MailPublishException {
		logger.debug("Implementation for publishing mail through SendGrid");

		try {

			// Prepare Http request for publishing email
			APIHttpRequest request = new APIHttpRequest(API_HOST + API_URL_MAIL_PUBLISH);
			request.setAuthentication(getAuthentication());
			request.setHeaders(getHeaders());

			String emailContent = prepareBodyContent(email);
			logger.debug("=====> emailContent :: " + emailContent);
			request.setBody(emailContent);

			// Validate if response is successful by validating the response code. If not, throw exception
			APIHttpResponse response = httpWrapper.handlePost(request);
			if(response.getCode() != MAIL_PUBLISH_RESPONSE_CODE_SUCCESS) { 
				throw new MailPublishException(ResponseCodes.FAILURE, "SendGrid email publish failed due to error code : "+response.getCode()); 
			}
			 
		} catch (Exception e) {
			throw new MailPublishException(ResponseCodes.FAILURE, "Error occurred while sending email from SendGrid",
					e);
		}
	}

	// Create body content in json format that should be sent as body to SendGrid mail send API
	private String prepareBodyContent(Email email) throws Exception {

		// Prepare email content object with all necessary data
		Messages messages = new Messages(email.getTo().stream().map(e -> new To(e)).collect(Collectors.toList()),
				email.getSubject(), email.getBody(), new From(DEFAULT_FROM_ADDRESS));

		// Validate if there are email address for CC. If yes, add them to messages
		if (CollectionUtils.isNotEmpty(email.getCc())) {
			messages.setCc(email.getCc().stream().map(e -> new Cc(e)).collect(Collectors.toList()));
		}

		// Validate if there are email address for BCC. If yes, add them to messages
		if (CollectionUtils.isNotEmpty(email.getBcc())) {
			messages.setBcc(email.getBcc().stream().map(e -> new Bcc(e)).collect(Collectors.toList()));
		}

		EmailContent content = new EmailContent(Arrays.asList(messages));
		
		// Convert email content object to json string
		return new ObjectMapper().writeValueAsString(content);
	}

}
