package com.microservice.user.user.domain.usecase;

import com.microservice.user.user.domain.entities.User;
import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.ports.UserPorts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserByEmailTest {
    @Mock
    private UserPorts userPorts;

    private UserByEmail userByEmail;

    @BeforeEach
    void setUp() {
        userByEmail = new UserByEmail(userPorts);
    }

    @Test
    void shouldGetUser_whenEmailExist() {
        // Arrange
        String userEmail = "johndoe@example.com";
        User user = getBasicUser();

        when(userPorts.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Act
        UserDto userDto = userByEmail.execute(userEmail);

        // Assert
        assertAll(
                () -> assertEquals("John", userDto.getName()),
                () -> assertEquals("Doe", userDto.getLastName()),
                () -> assertEquals("johndoe@example.com", userDto.getEmail())
        );
    }

    private User getBasicUser(){
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");

        return user;
    }

}