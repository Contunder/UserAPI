package com.microservice.user.user.domain.usecase;

import com.microservice.user.exception.UserAPIException;
import com.microservice.user.user.domain.gateway.RegisterDto;
import com.microservice.user.user.domain.mapper.UserMapper;
import com.microservice.user.user.domain.ports.RolePorts;
import com.microservice.user.user.domain.ports.UserPorts;
import com.microservice.user.user.domain.entities.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Register {

    private final UserPorts userPorts;
    private final RolePorts rolePorts;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Register(UserPorts userPorts, RolePorts rolePorts, PasswordEncoder passwordEncoder) {
        this.userPorts = userPorts;
        this.rolePorts = rolePorts;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = new UserMapper();
    }


    public String execute(RegisterDto registerDto) {

        if (userPorts.existsByEmail(registerDto.getEmail())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role userRole = rolePorts.findByName("ROLE_USER")
                .orElseThrow(
                        () -> new UserAPIException(HttpStatus.NOT_FOUND, "Role Not Found")
                );

        userPorts.save(userMapper.mapToModel(registerDto, userRole));

        return "User registered successfully!.";
    }

}
