package com.microservice.user.security;

import com.microservice.user.user.domain.entities.Role;
import com.microservice.user.user.domain.entities.User;
import com.microservice.user.user.domain.ports.UserPorts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

class CustomUserDetailsServiceTest {

    @Mock
    private UserPorts userPorts;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService(userPorts);
    }

    @Test
    void testLoadUserByUsername() {
        // Arrange
        String userEmail = "test@example.com";
        User user = new User();
        user.setEmail(userEmail);
        user.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singleton(role));

        Mockito.when(userPorts.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

        // Assert
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());

        Set<SimpleGrantedAuthority> expectedAuthorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        Assertions.assertEquals(expectedAuthorities, userDetails.getAuthorities());

        Mockito.verify(userPorts).findByEmail(userEmail);
    }

    @Test
    void testLoadUserByUsername_UsernameNotFoundException() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        Mockito.when(userPorts.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(userEmail);
        });

        Mockito.verify(userPorts).findByEmail(userEmail);
    }

}
