package com.microservice.user.infrastructure.mapper;

import com.microservice.user.domain.entities.User;
import com.microservice.user.infrastructure.entity.TrackingEntity;
import org.springframework.stereotype.Component;

@Component
public class TrackingMapper {

    public TrackingEntity mapToEntity(User user, String eventType) {
        return TrackingEntity.builder()
                .email(user.getEmail())
                .createType(eventType)
                .createId(user.getId())
                .build();
    }

}
