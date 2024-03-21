package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.mapper.UserMapper;
import com.microservice.user.domain.ports.UserPorts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
    private final UserPorts userPorts;
    private final UserMapper userMapper;

    public UpdateUser(UserPorts userPorts) {
        this.userPorts = userPorts;
        this.userMapper = new UserMapper();
    }

    public UserDto execute(UserDto userDto) {

        User user = userPorts.findByEmail(userDto.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userDto.getEmail()));

        return userMapper.mapToDTO(userPorts.save(userMapper.mapUpdateToModel(userDto, user)));
    }
}
