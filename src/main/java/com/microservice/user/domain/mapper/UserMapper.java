package com.microservice.user.domain.mapper;

import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.entities.User;

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

    public User mapUpdateToModel(UserDto userDto, User user) {
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
        user.setAddress(userDto.getAddress());
        user.setZipCode(userDto.getZipCode());
        user.setCity(userDto.getCity());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
