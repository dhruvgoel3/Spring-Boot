package com.project.Fitness_Tracker.controller;

import com.project.Fitness_Tracker.DTO.AuthResponse;
import com.project.Fitness_Tracker.DTO.LoginRequest;
import com.project.Fitness_Tracker.DTO.RegisterRequest;
import com.project.Fitness_Tracker.DTO.UserResponse;
import com.project.Fitness_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
