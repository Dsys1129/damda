package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException(String message) {
        super(message);
        this.code = "InvalidCredentials";
        this.httpStatus = HttpStatus.UNAUTHORIZED.value();
    }
}
