package com.spring.bikesshop.exceptions;

//Clase para representar la respuesta de error
public class ErrorResponse {
	private String message;
	private int statusCode;
	private String errorType;

	public ErrorResponse() {
	}

	public ErrorResponse(String message, int statusCode, String errorType) {
		this.message = message;
		this.statusCode = statusCode;
		this.errorType = errorType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
}
