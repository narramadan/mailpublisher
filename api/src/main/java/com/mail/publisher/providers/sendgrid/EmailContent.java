package com.mail.publisher.providers.sendgrid;

import java.util.List;

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
	private List<Personalizations> personalizations;
	private String subject;
	private List<Content> content;
	private From from;
}

@Data
@RequiredArgsConstructor
class Personalizations {
    private final List<To> to;
    private List<Bcc> bcc;
    private List<Cc> cc;
}

@Data
@AllArgsConstructor
class To {
    private String email;
}

@Data
@AllArgsConstructor
class Cc {
    private String email;
}

@Data
@AllArgsConstructor
class Bcc {
    private String email;
}

@Data
@AllArgsConstructor
class Content {
    private String value;
    private String type;
}

@Data
@AllArgsConstructor
class From {
	private String email;
}