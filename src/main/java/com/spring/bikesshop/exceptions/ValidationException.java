package com.spring.bikesshop.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

//Maneja errores de validaci�n
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;

	public ValidationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
	
	// M�todo para devolver estado
	public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
