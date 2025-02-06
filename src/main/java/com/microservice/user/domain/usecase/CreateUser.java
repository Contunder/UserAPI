package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.ports.UserPort;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {

    private final UserPort userPort;

    public CreateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public User execute(User user) {

        return userPort.createUser(user, "Create User");
    }

}
