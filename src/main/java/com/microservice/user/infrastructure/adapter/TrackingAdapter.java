package com.microservice.user.infrastructure.adapter;

import com.microservice.user.domain.entities.User;
import com.microservice.user.infrastructure.dao.TrackingDAO;
import com.microservice.user.infrastructure.mapper.TrackingMapper;
import org.springframework.stereotype.Component;

@Component
public class TrackingAdapter {

    private final TrackingDAO trackingDAO;
    private final TrackingMapper trackingMapper;

    public TrackingAdapter(TrackingDAO trackingDAO, TrackingMapper trackingMapper) {
        this.trackingDAO = trackingDAO;
        this.trackingMapper = trackingMapper;
    }

    public User sendTrackingEvent(User user, String event) {
        trackingDAO.sendUserTrackingEvent(trackingMapper.mapToEntity(user, event));

        return user;
    }
}
