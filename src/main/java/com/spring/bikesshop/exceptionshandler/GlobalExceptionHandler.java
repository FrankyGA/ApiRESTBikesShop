package com.spring.bikesshop.exceptionshandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.bikesshop.exceptions.ConflictException;
import com.spring.bikesshop.exceptions.ErrorResponse;
import com.spring.bikesshop.exceptions.ForbiddenException;
import com.spring.bikesshop.exceptions.InternalServerErrorException;
import com.spring.bikesshop.exceptions.ResourceNotFoundException;
import com.spring.bikesshop.exceptions.UnauthorizedException;
import com.spring.bikesshop.exceptions.UnsupportedOperationException;
import com.spring.bikesshop.exceptions.ValidationException;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ResponseBody
	 * 
	 * @ExceptionHandler(ResourceNotFoundException.class) public
	 * ResponseEntity<ErrorResponse>
	 * handleResourceNotFoundException(ResourceNotFoundException ex) { ErrorResponse
	 * errorResponse = new ErrorResponse(ex.getMessage(),
	 * ex.getHttpStatus().value(), "Resource not found"); return
	 * ResponseEntity.status(ex.getHttpStatus()).body(errorResponse); }
	 * 
	 * @ResponseBody
	 * 
	 * @ExceptionHandler(ValidationException.class) public
	 * ResponseEntity<ErrorResponse> handleValidationException(ValidationException
	 * ex) { ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
	 * ex.getHttpStatus().value(), "Bad request error"); return
	 * ResponseEntity.status(ex.getHttpStatus()).body(errorResponse); }
	 */
    
	@ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Authorized validation error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
	@ResponseBody
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Internal server error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
	@ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Access denied error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
	@ResponseBody
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value(), "Request conflicts error");
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
	@ResponseBody
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

