package com.microservice.user.domain.gateway;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class UserDto {
    private String name;
    private String lastName;
    private Date birthday;
    private String address;
    private String zipCode;
    private String city;
    private String email;
}
