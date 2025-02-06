package com.microservice.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Date birthday;
    private String address;
    private String zipCode;
    private String city;
    @Column(nullable = false, unique = true)
    private String email;


}
