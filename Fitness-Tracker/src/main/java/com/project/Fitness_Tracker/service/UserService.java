package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.RegisterRequest;
import com.project.Fitness_Tracker.DTO.UserResponse;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //  Create User
    public UserResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build(); // whole work is done by it , object will be created

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User savedUser) {
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdateAt());
        return response;
    }


}
