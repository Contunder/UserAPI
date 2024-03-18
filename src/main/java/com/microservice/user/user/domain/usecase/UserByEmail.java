package com.microservice.user.user.domain.usecase;

import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.mapper.UserMapper;
import com.microservice.user.user.domain.ports.UserPorts;
import com.microservice.user.user.domain.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserByEmail {

    private final UserPorts userPorts;
    private final UserMapper userMapper;

    public UserByEmail(UserPorts userPorts){
        this.userPorts = userPorts;
        this.userMapper = new UserMapper();
    }

    public UserDto execute(String userEmail) {
        User user = userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail));

        return userMapper.mapToDTO(user);
    }

}
