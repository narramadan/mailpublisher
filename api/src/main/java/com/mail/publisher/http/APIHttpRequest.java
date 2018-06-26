package com.mail.publisher.http;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class APIHttpRequest {

	private final String url;
	private Authentication authentication;
	private Map<String, String> headers;
	private Map<String, String> params;
	private String body;
}