package com.microservice.user.application.mapper;

import com.microservice.user.application.entity.UserDto;
import com.microservice.user.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapToDTO(User user){
        return UserDto.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .address(user.getAddress())
                .zipCode(user.getZipCode())
                .city(user.getCity())
                .build();
    }

    public User mapToModel(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .birthday(userDto.getBirthday())
                .address(userDto.getAddress())
                .zipCode(userDto.getZipCode())
                .city(userDto.getCity())
                .email(userDto.getEmail())
                .build();
    }

}
