package com.mail.publisher.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResponse {

	private String field;
	private String message;
}
