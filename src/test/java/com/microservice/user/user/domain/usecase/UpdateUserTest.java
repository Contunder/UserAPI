package com.microservice.user.user.domain.usecase;

import com.microservice.user.user.domain.gateway.RegisterDto;
import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.ports.RolePorts;
import com.microservice.user.user.domain.ports.UserPorts;
import com.microservice.user.user.domain.entities.Role;
import com.microservice.user.user.domain.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserTest {

    @Mock
    private UserPorts userPorts;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RolePorts rolePorts;

    private UpdateUser updateUser;

    @BeforeEach
    void setup() {
        updateUser = new UpdateUser(userPorts, passwordEncoder, rolePorts);
    }

    @Test
    void shouldUpdateUser_whenUserIsConnected_andHaveNewUserInfos() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("email");
        registerDto.setPassword("password");
        registerDto.setName("new name");

        User user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setPassword("password");
        user.setName("name");

        when(userPorts.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("MyHash");
        when(rolePorts.findByName("ROLE_USER")).thenReturn(Optional.of(new Role(1, "ROLE_USER")));
        when(userPorts.save(any())).thenReturn(user);

        // Act
        UserDto result = updateUser.execute(registerDto);

        // Assert
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setEmail("email");
        expectedUserDto.setName("new name");

        assertEquals(expectedUserDto, result);
    }
}