package com.innovecture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestReceivedException extends RuntimeException {

	private static final long serialVersionUID = 4670378082122639209L;

	public BadRequestReceivedException(final String message) {
		super(message);
	}
}
