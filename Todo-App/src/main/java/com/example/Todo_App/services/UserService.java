package com.example.Todo_App.services;

import com.example.Todo_App.DTO.UserRequestDTO;
import com.example.Todo_App.DTO.UserResponseDTO;
import com.example.Todo_App.entity.User;
import com.example.Todo_App.mappers.UserMapper;
import com.example.Todo_App.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Create User
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new RuntimeException("Username already exits :-" + requestDTO.getUsername());
        }

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already exists :- " + requestDTO.getEmail());
        }

        User user = userMapper.toEntity(requestDTO); // convert dto into entity
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    /**
     * Get user by ID
     */
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return userMapper.toResponseDTO(user);
    }

    /**
     * Get all users
     */
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)  // Convert each User to UserResponseDTO
                .collect(Collectors.toList());
    }

    /**
     * Update user
     */
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Check if new username is taken by another user
        if (!user.getUsername().equals(requestDTO.getUsername())
                && userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + requestDTO.getUsername());
        }

        // Check if new email is taken by another user
        if (!user.getEmail().equals(requestDTO.getEmail())
                && userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + requestDTO.getEmail());
        }

        // Update entity from DTO
        userMapper.updateEntityFromDTO(user, requestDTO);

        // Save updated entity
        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    /**
     * Delete user
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}
