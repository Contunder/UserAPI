package com.microservice.user.user.domain.usecase;

import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.mapper.UserMapper;
import com.microservice.user.user.domain.ports.UserPorts;
import com.microservice.user.user.domain.entities.User;
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

        List<User> users = userPorts.findAll();
        users = users.stream().filter(user -> user != actualUser).collect(Collectors.toList());

        return users.stream().map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }

}
