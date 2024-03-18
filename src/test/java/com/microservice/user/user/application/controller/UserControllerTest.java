package com.microservice.user.user.application.controller;

import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.application.UserController;
import com.microservice.user.user.domain.usecase.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserByEmail userByEmail;
    @Mock
    private AllUser allUser;
    @Mock
    private DeleteUser deleteUser;
    @Mock
    private UpdateUser updateUser;

    private UserController userController;

    @BeforeEach
    void setup() {
        userController = new UserController(userByEmail, allUser, deleteUser, updateUser, null, null);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        UserDto userDto = new UserDto();
        Mockito.when(userByEmail.execute(email)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.getUserByEmail(email);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(userDto, response.getBody());
        Mockito.verify(userByEmail).execute(email);
    }

}
