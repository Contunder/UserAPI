package com.microservice.user.infrastructure.adapter;

import com.microservice.user.domain.entities.User;
import com.microservice.user.domain.exception.UserAPIException;
import com.microservice.user.domain.ports.UserPort;
import com.microservice.user.infrastructure.dao.UserDAO;
import com.microservice.user.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class UserAdapter implements UserPort {

    public static final String USER_NOT_FOUND = "User not found with email: ";
    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final TrackingAdapter trackingAdapter;


    public UserAdapter(UserDAO userDAO, UserMapper userMapper, TrackingAdapter trackingAdapter) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.trackingAdapter = trackingAdapter;
    }

    @Override
    public User createUser(User user, String trackingEvent) {
        return trackingAdapter.sendTrackingEvent(
                userMapper.mapToModel(
                        userDAO.save(
                                userMapper.mapToEntity(user)
                        )
                ),
                trackingEvent
        );
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.findAll().stream()
                .map(userMapper::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String email) throws UserAPIException {
        return userMapper.mapToModel(
                userDAO.findByEmail(email)
                        .orElseThrow(() -> new UserAPIException(USER_NOT_FOUND + email, BAD_REQUEST))
        );
    }

    @Override
    public User deleteUser(User user, String trackingEvent) throws UserAPIException {
        userDAO.delete(userDAO.findByEmail(user.getEmail()).orElseThrow(
                    () -> new UserAPIException(USER_NOT_FOUND + user.getEmail(), BAD_REQUEST)
                )
        );

        return trackingAdapter.sendTrackingEvent(user, trackingEvent);
    }

    @Override
    public User updateUser(User user, String trackingEvent) throws UserAPIException {
        return trackingAdapter.sendTrackingEvent(
                userMapper.mapToModel(
                        userDAO.save(userMapper.mapUpdateToEntity(
                                        user,
                                        userDAO.findByEmail(user.getEmail()).orElseThrow(
                                                () -> new UserAPIException(USER_NOT_FOUND + user.getEmail(), BAD_REQUEST)
                                        )
                                )
                        )
                ),
                trackingEvent
        );
    }

}
