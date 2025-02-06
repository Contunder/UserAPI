package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.exception.UserAPIException;
import com.microservice.user.domain.ports.UserPort;
import org.springframework.stereotype.Component;

@Component
public class UserByEmail {

    private final UserPort userPort;

    public UserByEmail(UserPort userPort){
        this.userPort = userPort;
    }

    public User execute(String userEmail) throws UserAPIException {

        return userPort.findUserByEmail(userEmail);
    }

}
