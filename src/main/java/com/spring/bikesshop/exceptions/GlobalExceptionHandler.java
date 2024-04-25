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
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Bad request error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Authorized validation error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Internal server error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Access denied error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Request conflicts error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Unsupported operation error");
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

