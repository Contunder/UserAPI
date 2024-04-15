package com.microservice.user.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.ports.UserPorts;
import com.microservice.user.domain.usecase.DeleteUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserTest {
    @Mock
    private UserPorts userPorts;
    private DeleteUser deleteUser;

    @BeforeEach
    void setup() {
        deleteUser = new DeleteUser(userPorts);
    }

//    @Test
//    void shouldDeleteUser_whenHaveUserEmail() {
//        // Arrange
//        String userEmail = "johndoe@example.com";
//
//        when(userPorts.findByEmail(userEmail)).thenReturn(Optional.of(new User()));
//
//        // Act
//        String result = deleteUser.execute(userEmail);
//
//        // Assert
//        assertEquals("User johndoe@example.com deleted successfully.", result);
//    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        // Arrange
        String userEmail = "johndoe@example.com";

        when(userPorts.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            deleteUser.execute(userEmail);
        });
    }

}