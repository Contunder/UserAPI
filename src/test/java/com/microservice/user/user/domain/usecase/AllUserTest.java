package com.microservice.user.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.ports.UserPorts;
import com.microservice.user.domain.usecase.AllUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllUserTest {
    @Mock
    private UserPorts userPorts;

    private AllUser allUser;

    @BeforeEach
    void setUp() {
        allUser = new AllUser(userPorts);
    }

    @Test
    void shouldGetAllUser_whenUserIsConnected() {
        // Arrange
        String userEmail = "johndoe@example.com";
        User user = getBasicUser();

        List<User> users = new ArrayList<>();
        users.add(user);

        when(userPorts.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(userPorts.findAll()).thenReturn(users);

        // Act
        List<UserDto> userDtos = allUser.execute(userEmail);

        // Assert
        assertEquals(0, userDtos.size());
    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        // Arrange
        String userEmail = "johndoe@example.com";

        when(userPorts.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            allUser.execute(userEmail);
        });
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