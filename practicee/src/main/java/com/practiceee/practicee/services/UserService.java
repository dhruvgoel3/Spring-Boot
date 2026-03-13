package com.practiceee.practicee.services;

import com.practiceee.practicee.dtos.UserMapper;
import com.practiceee.practicee.dtos.UserRequestDTO;
import com.practiceee.practicee.dtos.UserResponseDTO;
import com.practiceee.practicee.entitiy.User;
import com.practiceee.practicee.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Create a User
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);
        User savedUser = userRepository.save(user);
        return userMapper.userResponseDTO(savedUser);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User user) {

        Optional<User> response = userRepository.findById(id);

        if (response.isPresent()) {
            User existingUser = response.get();

            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteById(Long Id) {
        userRepository.deleteById(Id);
    }
}
