package com.microservice.user.application.controller;

import com.microservice.user.application.entity.UserDto;
import com.microservice.user.application.mapper.UserMapper;
import com.microservice.user.application.presenter.Presenter;
import com.microservice.user.application.security.JwtAuthenticationFilter;
import com.microservice.user.application.security.JwtTokenProvider;
import com.microservice.user.domain.exception.UserAPIException;
import com.microservice.user.domain.usecase.AllUser;
import com.microservice.user.domain.usecase.CreateUser;
import com.microservice.user.domain.usecase.DeleteUser;
import com.microservice.user.domain.usecase.UpdateUser;
import com.microservice.user.domain.usecase.UserByEmail;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserByEmail userByEmail;
    private final Presenter presenter;
    private final AllUser allUser;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;
    private final CreateUser createUser;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    public UserController(UserByEmail userByEmail, Presenter presenter, AllUser allUser, DeleteUser deleteUser, UpdateUser updateUser, CreateUser createUser, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider jwtTokenProvider, UserMapper userMapper) {
        this.userByEmail = userByEmail;
        this.presenter = presenter;
        this.allUser = allUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.createUser = createUser;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapper = userMapper;
    }

    @GetMapping(value = {"/email/{email}"})
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
        try {

            return presenter.presentSuccess(userByEmail.execute(email));
        } catch (UserAPIException userAPIException) {

            return presenter.presentFailure(userAPIException);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        try {

            return presenter.presentSuccess(updateUser.execute(userMapper.mapToModel(userDto)));
        } catch (UserAPIException userAPIException) {

            return presenter.presentFailure(userAPIException);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {

        return new ResponseEntity<>(createUser.execute(userMapper.mapToModel(userDto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        try {
            String token = jwtAuthenticationFilter.getTokenFromRequest(request);
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            return presenter.presentSuccess(deleteUser.execute(email));
        } catch (UserAPIException userAPIException) {

            return presenter.presentFailure(userAPIException);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = {"/actual"})
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        try {
            String token = jwtAuthenticationFilter.getTokenFromRequest(request);
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            return presenter.presentSuccess(userByEmail.execute(email));
        } catch (UserAPIException userAPIException) {

            return presenter.presentFailure(userAPIException);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(HttpServletRequest request) {
        try {
            String token = jwtAuthenticationFilter.getTokenFromRequest(request);
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            return presenter.presentSuccess(allUser.execute(email));
        } catch (UserAPIException userAPIException) {

            return presenter.presentFailure(userAPIException);
        }
    }

}
