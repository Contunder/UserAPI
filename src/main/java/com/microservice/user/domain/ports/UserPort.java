package com.microservice.user.domain.ports;

import com.microservice.user.domain.entities.User;

import java.util.List;

public interface UserPort {

    User createUser(User user, String trackingEvent);

    List<User> findAllUser();

    User findUserByEmail(String email);

    User deleteUser(User user, String trackingEvent);

    User updateUser(User user, String trackingEvent);
}
