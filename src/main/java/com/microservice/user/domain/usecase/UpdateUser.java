package com.microservice.user.domain.usecase;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.gateway.TrackingDTO;
import com.microservice.user.domain.gateway.UserDto;
import com.microservice.user.domain.mapper.TrackingMapper;
import com.microservice.user.domain.mapper.UserMapper;
import com.microservice.user.domain.ports.UserPorts;
import com.microservice.user.utils.JsonBodyHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class UpdateUser {
    private final UserPorts userPorts;
    private final UserMapper userMapper;
    private final TrackingMapper trackingMapper;
    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public UpdateUser(UserPorts userPorts) {
        this.userPorts = userPorts;
        this.userMapper = new UserMapper();
        this.trackingMapper = new TrackingMapper();
        this.client = HttpClient.newHttpClient();
    }

    public UserDto execute(UserDto userDto) {

        User user = userPorts.findByEmail(userDto.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userDto.getEmail()));

        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(trackingMapper.mapToDto(user, "Update User")).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingDTO.class));

        return userMapper.mapToDTO(userPorts.save(userMapper.mapUpdateToModel(userDto, user)));
    }
}
