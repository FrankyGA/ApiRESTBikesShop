package com.spring.bikesshop.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Resource not found");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Validation error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
	
	/*
	 * @ExceptionHandler(ResourceNotFoundException.class) public
	 * ResponseEntity<String>
	 * handleResourceNotFoundException(ResourceNotFoundException ex) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); }
	 * 
	 * @ExceptionHandler(ValidationException.class) public ResponseEntity<String>
	 * handleValidationException(ValidationException ex) { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); }
	 */
}

