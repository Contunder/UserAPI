package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.mapper.UserMapper;
import com.microservice.user.domain.ports.UserPorts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllUser {

    private final UserPorts userPorts;
    private final UserMapper userMapper;

    public AllUser(UserPorts userPorts){
        this.userPorts = userPorts;
        this.userMapper = new UserMapper();
    }

    public List<UserDto> execute(String userEmail) {

        User actualUser = userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail));

        return userPorts.findAll().stream()
                .filter(user -> user != actualUser)
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }

}
