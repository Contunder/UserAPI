package com.microservice.user.domain.usecase;

import com.microservice.user.infrastructure.entity.User;
import com.microservice.user.domain.gateway.TrackingDTO;
import com.microservice.user.domain.mapper.TrackingMapper;
import com.microservice.user.infrastructure.dao.UserPorts;
import com.microservice.user.JsonBodyHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class CreateUser {
    private final UserPorts userPorts;

    private final TrackingMapper trackingMapper;
    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public CreateUser(UserPorts userPorts) {
        this.userPorts = userPorts;
        this.trackingMapper = new TrackingMapper();
        this.client = HttpClient.newHttpClient();
    }

    public User execute(User user) {

        User newUser = userPorts.save(user);

        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(trackingMapper.mapToDto(newUser, "Create User")).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingDTO.class));

        return newUser;
    }

}
