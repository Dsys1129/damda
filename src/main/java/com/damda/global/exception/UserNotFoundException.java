package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message) {
        super(message);
        this.code = "USER_NOT_FOUND";
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
    }
}
