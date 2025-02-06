package com.microservice.user.application.presenter;

import com.microservice.user.application.entity.UserDto;
import com.microservice.user.application.mapper.UserMapper;
import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.exception.UserAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Presenter {

    private final UserMapper userMapper;

    public Presenter(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserDto> presentSuccess(User user) {
        return new ResponseEntity<>(userMapper.mapToDTO(user), HttpStatus.OK);
    }

    public ResponseEntity<List<UserDto>> presentSuccess(List<User> users) {
        return new ResponseEntity<>(users.stream().map(userMapper::mapToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<String> presentSuccess(String successResponse) {
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserAPIException> presentFailure(UserAPIException userAPIException) {
        return new ResponseEntity<>(userAPIException, HttpStatus.BAD_REQUEST);
    }

}