package com.practiceee.practicee.controllers;

import com.practiceee.practicee.dtos.UserRequestDTO;
import com.practiceee.practicee.dtos.UserResponseDTO;
import com.practiceee.practicee.entitiy.User;
import com.practiceee.practicee.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO response = userService.createUser(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User response = userService.updateUser(id, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> response = userService.getUserById(id);
        return ResponseEntity.ok(response);

    }


    @DeleteMapping
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }


}
