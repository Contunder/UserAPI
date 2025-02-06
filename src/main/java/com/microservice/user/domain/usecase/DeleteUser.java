package com.microservice.user.domain.usecase;

import com.microservice.user.domain.exception.UserAPIException;
import com.microservice.user.domain.ports.UserPort;
import org.springframework.stereotype.Component;

@Component
public class DeleteUser {

    private final UserPort userPort;

    public DeleteUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public String execute(String userEmail) throws UserAPIException {
        userPort.deleteUser(
                userPort.findUserByEmail(userEmail),
                "Delete User"
        );

        return "User " + userEmail + " deleted successfully.";
    }

}
