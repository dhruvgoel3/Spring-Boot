package com.example.Todo_App.services;

import com.example.Todo_App.entity.User;
import com.example.Todo_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    // UpdateUser
    public User updateUser(Long id, User updatedUser) {
        User exixtingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No User foud with this Id.;"));
        exixtingUser.setEmail(updatedUser.getEmail());
        exixtingUser.setPassword(updatedUser.getPassword());
        return updatedUser;
    }

    // GetAllUser
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //GetByID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // DeleteAllUser
    public void deleteALlUser() {
        userRepository.deleteAll();
    }

    // DeleteById
    public void deleteByID(Long id) {
        userRepository.deleteById(id);
    }
}
