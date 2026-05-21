package com.project.ecombackend.controllers;

import com.project.ecombackend.entity.User;
import com.project.ecombackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    }@GetMapping("/api/getOneUser{id}")
    public List<User> getOneUser(@PathVariable Long userId) {
        return userService.fetchAllUser();
    }

    @PostMapping("/api/createUsers")
    public List<User> createUser(@RequestBody User user) {
        userService.createUsers(user);
        return userList;
    }

}
