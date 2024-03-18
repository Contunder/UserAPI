package com.microservice.user.user.domain.gateway;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;

}
