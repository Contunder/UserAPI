package com.microservice.user.application.presenter;

import com.microservice.user.application.entity.UserDto;
import com.microservice.user.application.exception.UserAPIException;
import com.microservice.user.application.mapper.UserMapper;
import com.microservice.user.infrastructure.entity.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Presenter {

    private final UserMapper userMapper;

    public Presenter(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserDto> presentSuccess(User user) {
        return new ResponseEntity<>(userMapper.mapToDTO(user), HttpStatus.OK);
    }

    public ResponseEntity<String> presentSuccess(String successResponse) {
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserAPIException> presentFailure(ExecutionControl.UserException userException) {
        return new ResponseEntity<>(
                new UserAPIException(HttpStatus.BAD_REQUEST, userException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}