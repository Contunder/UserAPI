package com.microservice.user.user.application.controller;

import com.microservice.user.application.UserController;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.usecase.AllUser;
import com.microservice.user.domain.usecase.CreateUser;
import com.microservice.user.domain.usecase.DeleteUser;
import com.microservice.user.domain.usecase.UpdateUser;
import com.microservice.user.domain.usecase.UserByEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

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
    @Mock
    private CreateUser createUser;

    private UserController userController;

    @BeforeEach
    void setup() {
        userController = new UserController(userByEmail, allUser, deleteUser, updateUser, createUser, null, null);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        UserDto userDto = UserDto.builder().build();
        Mockito.when(userByEmail.execute(email)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.getUserByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(userByEmail).execute(email);
    }

}
