package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomException {
    public ForbiddenException(String message) {
        super(message);
        this.code = "Forbidden";
        this.httpStatus = HttpStatus.FORBIDDEN.value();
    }
}
