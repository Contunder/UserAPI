package com.microservice.user.infrastructure.dao;

import com.microservice.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
