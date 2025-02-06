package com.microservice.user.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private String name;
    private String lastName;
    private Date birthday;
    private String address;
    private String zipCode;
    private String city;
    private String email;
    private Role role;

}
