package com.microservice.user.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserAPIException extends RuntimeException {

    private final String message;
    @Getter
    private final HttpStatus status;

    public UserAPIException(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
