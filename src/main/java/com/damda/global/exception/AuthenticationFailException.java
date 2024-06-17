package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationFailException extends CustomException {

    public AuthenticationFailException(String message) {
        super(message);
        this.code = "AuthenticationFail";
        this.message = message;
        this.httpStatus = HttpStatus.UNAUTHORIZED.value();
    }
}
