package com.microservice.user.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackingEntity {

    private String email;
    private String createType;
    private long createId;

}
