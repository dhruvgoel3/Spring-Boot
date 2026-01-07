package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.RegisterRequest;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.repository.UserRepository;
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
    public User register(RegisterRequest request) {
        User user = new User(
                null,
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                Instant.parse("2007-12-03T10:15:30Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
                Instant.parse("2007-12-03T10:15:30Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
                List.of(),
                List.of()


        );
        return userRepository.save(user);
    }


}
