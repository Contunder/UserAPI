package com.microservice.user.domain.gateway;

import com.microservice.user.domain.entities.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
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
