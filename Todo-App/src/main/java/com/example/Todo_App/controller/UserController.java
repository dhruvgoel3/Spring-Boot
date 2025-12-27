package com.example.Todo_App.controller;

import com.example.Todo_App.entity.User;
import com.example.Todo_App.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    protected UserService userService;

    @PostMapping
    public User createUser(@RequestBody  User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public List<User> getALlUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteALlUser();
    }

    @DeleteMapping("/{id}")
    public void deleteUserByID(@PathVariable Long id) {
        userService.deleteByID(id);
    }
}
