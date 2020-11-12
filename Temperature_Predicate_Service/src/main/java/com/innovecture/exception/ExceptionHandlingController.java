package com.innovecture.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

	@Autowired
	ExceptionResponse exceptionResponse;

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ExceptionResponse> internalServerException(InternalServerException exception) {
		exceptionResponse.setErrorStatus("Internal server error");
		exceptionResponse.setErrorMessage(exception.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadRequestReceivedException.class)
	public ResponseEntity<ExceptionResponse> badRequestReceivedException(BadRequestReceivedException exception) {
		exceptionResponse.setErrorStatus("Bad Request");
		exceptionResponse.setErrorMessage(exception.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
