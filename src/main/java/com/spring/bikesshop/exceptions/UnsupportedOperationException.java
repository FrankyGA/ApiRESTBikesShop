package com.spring.bikesshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Error 405: operaciones no admitidas
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UnsupportedOperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;

    public UnsupportedOperationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

