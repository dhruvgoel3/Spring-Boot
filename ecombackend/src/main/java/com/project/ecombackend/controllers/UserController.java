package com.project.ecombackend.controllers;

import com.project.ecombackend.entity.User;
import com.project.ecombackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private List<User> userList = new ArrayList<>();


    private final UserService userService;

    @GetMapping("/api/getAllUsers")
    public List<User> getAllUsers() {
        return userService.fetchAllUser();

    }

    @GetMapping("/api/getOneUser{id}")
    public List<User> getOneUser(@PathVariable Long userId) {
        return userService.fetchAllUser();
    }

    @PostMapping("/api/createUsers")
    public List<User> createUser(@RequestBody User user) {
        userService.createUsers(user);
        return userList;
    }

    @PutMapping("/api/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean updated = userService.updateUser(id, user);
        if (updated) {
            return ResponseEntity.ok("User added sucessfully");
        }
        return ResponseEntity.notFound().build();
    }


}
