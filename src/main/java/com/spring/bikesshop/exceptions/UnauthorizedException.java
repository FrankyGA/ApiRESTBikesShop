package com.spring.bikesshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;

	public UnauthorizedException(String message) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}

	// Método para devolver estado
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
