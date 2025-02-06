package com.microservice.user.infrastructure.mapper;

import com.microservice.user.domain.entities.User;
import com.microservice.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapToEntity(User user){
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .address(user.getAddress())
                .zipCode(user.getZipCode())
                .city(user.getCity())
                .build();
    }

    public User mapToModel(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .birthday(userEntity.getBirthday())
                .address(userEntity.getAddress())
                .zipCode(userEntity.getZipCode())
                .city(userEntity.getCity())
                .email(userEntity.getEmail())
                .build();
    }

    public UserEntity mapUpdateToEntity(User user, UserEntity userEntity) {
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setLastName(user.getLastName());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setAddress(user.getAddress());
        userEntity.setZipCode(user.getZipCode());
        userEntity.setCity(user.getCity());
        userEntity.setEmail(user.getEmail());

        return userEntity;
    }

}
