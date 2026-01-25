package com.project.Fitness_Tracker.controller;

import com.project.Fitness_Tracker.DTO.RegisterRequest;
import com.project.Fitness_Tracker.DTO.UserResponse;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;


    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

//    @GetMapping
//    public List<UserResponse> getAllUsers()
//    {
//        return userService
//    }
//

}
