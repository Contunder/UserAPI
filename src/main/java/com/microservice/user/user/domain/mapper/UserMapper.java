package com.microservice.user.user.domain.mapper;

import com.microservice.user.user.domain.entities.Role;
import com.microservice.user.user.domain.gateway.RegisterDto;
import com.microservice.user.user.domain.gateway.UserDto;
import com.microservice.user.user.domain.entities.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public UserDto mapToDTO(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setBirthday(user.getBirthday());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setZipCode(user.getZipCode());
        userDto.setCity(user.getCity());
        userDto.setProfilPicture(user.getProfilePicture());

        return userDto;
    }

    public User mapUpdateToModel(RegisterDto registerDto, User user, Role userRole) {
        user.setName(registerDto.getName());
        user.setLastName(registerDto.getLastName());
        user.setBirthday(registerDto.getBirthday());
        user.setAddress(registerDto.getAddress());
        user.setZipCode(registerDto.getZipCode());
        user.setCity(registerDto.getCity());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setVerified(false);
        user.setDisabled(false);

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return user;
    }

    public User mapToModel(RegisterDto registerDto, Role userRole) {
        return mapUpdateToModel(registerDto, new User(), userRole);
    }

}
