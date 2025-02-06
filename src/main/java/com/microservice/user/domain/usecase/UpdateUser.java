package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.exception.UserAPIException;
import com.microservice.user.domain.ports.UserPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
    private final UserPort userPort;

    public UpdateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public User execute(User user) throws UserAPIException {

        return userPort.updateUser(user, "Update User");
    }

}
