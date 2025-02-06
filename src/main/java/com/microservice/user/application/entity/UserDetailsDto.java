package com.microservice.user.application.entity;

import com.microservice.user.domain.entities.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    String email;
    String password;
    Set<Role> roles;
}
