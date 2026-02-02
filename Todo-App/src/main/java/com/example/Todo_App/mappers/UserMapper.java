package com.example.Todo_App.mappers;


import com.example.Todo_App.DTO.UserRequestDTO;
import com.example.Todo_App.DTO.UserResponseDTO;
import com.example.Todo_App.entity.User;
import org.springframework.stereotype.Component;

/**
 * UserMapper - Converts between User Entity and DTOs
 * This is THE KEY to understanding DTOs!
 * We need to manually map fields between Entity and DTO objects.
 */
@Component
public class UserMapper {

    /**
     * Convert UserRequestDTO to User Entity
     * Used when creating a new user from client data
     */
    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());  // In real app, encrypt this first!
        return user;
    }

    /**
     * Convert User Entity to UserResponseDTO
     * Used when sending user data back to client
     * SECURITY: Notice we don't copy the password!
     */
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // NO password! This keeps it secure
        return dto;
    }

    /**
     * Update existing User entity from DTO
     * Used when updating user information
     */
    public void updateEntityFromDTO(User user, UserRequestDTO dto) {
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());  // In real app, encrypt this!
        }
    }
}