package com.microservice.user.user.domain.usecase;

import com.microservice.user.exception.UserAPIException;
import com.microservice.user.user.domain.entities.Role;
import com.microservice.user.user.domain.entities.User;
import com.microservice.user.user.domain.gateway.RegisterDto;
import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.mapper.UserMapper;
import com.microservice.user.user.domain.ports.RolePorts;
import com.microservice.user.user.domain.ports.UserPorts;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
    private final UserPorts userPorts;
    private final PasswordEncoder passwordEncoder;
    private final RolePorts rolePorts;
    private final UserMapper userMapper;

    public UpdateUser(UserPorts userPorts, PasswordEncoder passwordEncoder, RolePorts rolePorts) {
        this.userPorts = userPorts;
        this.passwordEncoder = passwordEncoder;
        this.rolePorts = rolePorts;
        this.userMapper = new UserMapper();
    }

    public UserDto execute(RegisterDto registerDto) {

        User user = userPorts.findByEmail(registerDto.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + registerDto.getEmail()));

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role userRole = rolePorts.findByName("ROLE_USER")
                .orElseThrow(
                        () -> new UserAPIException(HttpStatus.NOT_FOUND, "Role Not Found")
                );


        return userMapper.mapToDTO(userPorts.save(userMapper.mapUpdateToModel(registerDto, user, userRole)));
    }
}
