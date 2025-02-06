package com.microservice.user.domain.mapper;

import com.microservice.user.infrastructure.entity.User;
import com.microservice.user.domain.gateway.TrackingDTO;

public class TrackingMapper {

    public TrackingDTO mapToDto(User user, String eventType) {
        return TrackingDTO.builder()
                .email(user.getEmail())
                .createType(eventType)
                .createId(user.getId())
                .build();
    }

}
