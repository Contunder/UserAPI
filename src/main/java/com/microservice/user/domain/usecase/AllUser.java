package com.microservice.user.domain.usecase;

import com.microservice.user.infrastructure.entity.User;
import com.microservice.user.infrastructure.dao.UserPorts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllUser {

    private final UserPorts userPorts;

    public AllUser(UserPorts userPorts){
        this.userPorts = userPorts;
    }

    public List<User> execute(String userEmail) {

        User actualUser = userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail));

        return userPorts.findAll().stream()
                .filter(user -> user != actualUser)
                .collect(Collectors.toList());
    }

}
