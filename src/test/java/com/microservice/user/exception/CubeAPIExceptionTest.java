package com.microservice.user.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class CubeAPIExceptionTest {

    @Test
    void testCubeAPIException() {
        // Arrange
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Invalid request";

        // Act
        UserAPIException exception = new UserAPIException(status, message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

}