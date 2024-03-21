package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.ports.UserPorts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DeleteUser {

    private final UserPorts userPorts;

    public DeleteUser(UserPorts userPorts) {
        this.userPorts = userPorts;
    }

    public String execute(String userEmail) {

        User user = userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail));

        userPorts.delete(user);

        return "User " + userEmail + " deleted successfully.";
    }

}
