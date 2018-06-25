package com.mail.publisher.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Authentication {

	private String key;
	private String value;
}
