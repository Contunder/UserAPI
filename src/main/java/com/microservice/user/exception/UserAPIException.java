package com.microservice.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserAPIException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    private final String message;

    public UserAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
