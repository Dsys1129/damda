package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    protected String code;
    protected String message;
    protected int httpStatus;

    public CustomException(String message) {
        super(message);
        this.code = "InternalServerError";
        this.message = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
