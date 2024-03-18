package com.microservice.user.user.domain.usecase;

import com.microservice.user.user.domain.gateway.RegisterDto;
import com.microservice.user.user.domain.ports.RolePorts;
import com.microservice.user.user.domain.ports.UserPorts;
import com.microservice.user.user.domain.entities.Role;
import com.microservice.user.user.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterTest {

    @Mock
    private UserPorts userPorts;
    @Mock
    private RolePorts rolePorts;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Register register;

    @Test
    void shouldCreateUser_whenUserInfosIsComplete() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("john.doe@example.com");
        registerDto.setName("John");
        registerDto.setLastName("Doe");
        registerDto.setPassword("password");
        registerDto.setBirthday(new Date(2023,7,6));
        registerDto.setZipCode("12345");
        registerDto.setCity("City");
        registerDto.setAddress("Adress");

        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName("ROLE_USER");

        User savedUser = new User();
        savedUser.setId(1L);

        when(userPorts.existsByEmail(registerDto.getEmail())).thenReturn(false);
        when(rolePorts.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encoded_password");
        when(userPorts.save(Mockito.any(User.class))).thenReturn(savedUser);

        // Act
        String result = register.execute(registerDto);

        // Assert
        assertEquals("User registered successfully!.", result);
    }

}