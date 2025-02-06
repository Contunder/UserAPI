package com.microservice.user.domain.usecase;

import com.microservice.user.infrastructure.dao.UserPorts;
import com.microservice.user.infrastructure.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserByEmail {

    private final UserPorts userPorts;

    public UserByEmail(UserPorts userPorts){
        this.userPorts = userPorts;
    }

    public User execute(String userEmail) {

        return userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail)
                );
    }

}
