package com.mail.publisher.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIHttpResponse {

	private int code;
	private String message;
}
