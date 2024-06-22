package com.damda.global.exception;

import org.springframework.http.HttpStatus;

public class UserDuplicateException extends CustomException {

    public UserDuplicateException(String message) {
        super(message);
        this.code = "UserDuplicated";
        this.httpStatus = HttpStatus.CONFLICT.value();
    }
}
