package com.microservice.user.domain.usecase;

import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.mapper.UserMapper;
import com.microservice.user.domain.ports.UserPorts;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private final UserPorts userPorts;
    private final UserMapper userMapper;

    public CreateUser(UserPorts userPorts) {
        this.userPorts = userPorts;
        this.userMapper = new UserMapper();
    }

    public UserDto execute(UserDto userDto) {

        return userMapper.mapToDTO(userPorts.save(userMapper.mapToModel(userDto)));
    }

}
