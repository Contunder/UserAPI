package com.microservice.user.domain.usecase;

import com.microservice.user.infrastructure.entity.User;
import com.microservice.user.domain.gateway.TrackingDTO;
import com.microservice.user.domain.mapper.TrackingMapper;
import com.microservice.user.infrastructure.dao.UserPorts;
import com.microservice.user.JsonBodyHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class DeleteUser {

    private final UserPorts userPorts;

    private final TrackingMapper trackingMapper;
    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public DeleteUser(UserPorts userPorts) {
        this.userPorts = userPorts;
        this.trackingMapper = new TrackingMapper();
        this.client = HttpClient.newHttpClient();
    }

    public String execute(String userEmail) {

        User user = userPorts.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userEmail));

        userPorts.delete(user);

        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(trackingMapper.mapToDto(user, "Delete User")).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingDTO.class));

        return "User " + userEmail + " deleted successfully.";
    }

}
