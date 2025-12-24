package com.demo.first.controller;

import com.demo.first.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Integer, User> userMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userMap.putIfAbsent(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (!userMap.containsKey(user.getId())) return ResponseEntity.notFound().build();
        userMap.put(user.getId(), user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public List<User> getUser() {
        return new ArrayList<>(userMap.values());
    }

    @DeleteMapping("/{id}")
    public String deleteUserByID(@PathVariable int id) {
        userMap.remove(id);
        return "User Deleted";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUSerByID(@PathVariable int id) {
        if (!userMap.containsKey(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userMap.get(id));
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<User> getUserOrder(@PathVariable int id, @PathVariable int orderId) {
        System.out.println("ORDER ID:-" + orderId);
        if (!userMap.containsKey(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userMap.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        System.out.println(name);
        return ResponseEntity.ok(new ArrayList<>(userMap.values()));
    }

}
