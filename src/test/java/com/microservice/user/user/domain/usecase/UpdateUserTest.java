package com.microservice.user.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.ports.UserPorts;
import com.microservice.user.domain.usecase.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserTest {

    @Mock
    private UserPorts userPorts;
    private UpdateUser updateUser;

    @BeforeEach
    void setup() {
        updateUser = new UpdateUser(userPorts);
    }

//    @Test
//    void shouldUpdateUser_whenUserIsConnected_andHaveNewUserInfos() {
//        // Arrange
//        UserDto userDto = UserDto.builder()
//                .email("email")
//                .name("new name")
//                .build();
//
//        User user = new User();
//        user.setId(1);
//        user.setEmail("email");
//        user.setName("name");
//
//        when(userPorts.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
//        when(userPorts.save(any(User.class))).thenReturn(user);
//
//        // Act
//        updateUser.execute(userDto);
//
//        // Assert
//        verify(userPorts).findByEmail("email");
//        verify(userPorts).save(any(User.class));
//    }
}