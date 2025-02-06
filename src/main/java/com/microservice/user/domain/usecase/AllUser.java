package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.ports.UserPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllUser {

    private final UserPort userPort;

    public AllUser(UserPort userPort){
        this.userPort = userPort;
    }

    public List<User> execute(String userEmail) {

        return userPort.findAllUser().stream()
                .filter(user -> user != userPort.findUserByEmail(userEmail))
                .collect(Collectors.toList());
    }

}
