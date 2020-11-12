package com.innovecture.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class ExceptionResponse {

	private String errorStatus;
	private String errorMessage;

}
