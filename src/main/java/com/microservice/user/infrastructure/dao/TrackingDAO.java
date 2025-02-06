package com.microservice.user.infrastructure.dao;

import com.microservice.user.JsonBodyHandler;
import com.microservice.user.infrastructure.entity.TrackingEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class TrackingDAO {

    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public TrackingDAO() {
        this.client = HttpClient.newHttpClient();
    }

    public void sendUserTrackingEvent(TrackingEntity userTrackingEntity) {
        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(userTrackingEntity).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingEntity.class));
    }

}
