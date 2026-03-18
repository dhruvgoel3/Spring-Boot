package com.practiceee.practicee.dtos;

import com.practiceee.practicee.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserMapper - Converts between User Entity and DTOs
 * This is THE KEY to understanding DTOs!
 * We need to manually map fields between Entity and DTO objects.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Convert UserRequestDTO to User Entity
     * Used when creating a new user from client data
     */
    public User dtoToEntity(UserRequestDTO dto) {
        User user = this.modelMapper.map(dto, User.class);
        return user;

    }

    /**
     * Convert User Entity to UserResponseDTO
     * Used when sending user data back to client
     * SECURITY: Notice we don't copy the password !
     */
    public UserResponseDTO entityToDTO(User user) {
        UserResponseDTO dto = this.modelMapper.map(user, UserResponseDTO.class);
        return dto;
    }
}
