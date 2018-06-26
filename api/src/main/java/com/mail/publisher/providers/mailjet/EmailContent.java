package com.mail.publisher.providers.mailjet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Pojo Class representing the json that will be published to send email
 * @author MadanMeenu
 *
 */
@Data
@AllArgsConstructor
public class EmailContent {
	
	@JsonProperty("Messages")
	private List<Messages> messages;
	
}

@Data
@RequiredArgsConstructor
class Messages {
	@JsonProperty("To")
	private final List<To> to;
	
	@JsonProperty("Cc")
	private List<Cc> cc;
	
	@JsonProperty("Bcc")
	private List<Bcc> bcc;
    
	@JsonProperty("Subject")
	private final String subject;
	
	@JsonProperty("HTMLPart")
	private final String htmlPart;
	
	@JsonProperty("From")
	private final From from;
}

@Data
@AllArgsConstructor
class To {
	
	@JsonProperty("Email")
    private String email;
}

@Data
@AllArgsConstructor
class Cc {
	
	@JsonProperty("Email")
    private String email;
}

@Data
@AllArgsConstructor
class Bcc {
    
	@JsonProperty("Email")
	private String email;
}

@Data
@AllArgsConstructor
class From {
	
	@JsonProperty("Email")
	private String email;
}