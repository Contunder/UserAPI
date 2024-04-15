package com.microservice.user.application;

import com.microservice.user.domain.usecase.*;
import com.microservice.user.security.JwtAuthenticationFilter;
import com.microservice.user.security.JwtTokenProvider;
import com.microservice.user.domain.gateway.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserByEmail userByEmail;
    private final AllUser allUser;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;
    private final CreateUser createUser;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserByEmail userByEmail, AllUser allUser, DeleteUser deleteUser, UpdateUser updateUser, CreateUser createUser, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider jwtTokenProvider){
        this.userByEmail = userByEmail;
        this.allUser = allUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.createUser = createUser;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = {"/email/{email}"})
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userByEmail.execute(email));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto){

        return new ResponseEntity<>(updateUser.execute(userDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        return new ResponseEntity<>(createUser.execute(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest request){
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return new ResponseEntity<>(deleteUser.execute(email), HttpStatus.OK);
    }

    @GetMapping(value = {"/actual"})
    public ResponseEntity<UserDto> getUser(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return ResponseEntity.ok(userByEmail.execute(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser(HttpServletRequest request){
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return ResponseEntity.ok(allUser.execute(email));
    }

}
