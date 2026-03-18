package com.building.blog_app_backend.services;

import com.building.blog_app_backend.dto.UserDto;
import com.building.blog_app_backend.entities.User;
import com.building.blog_app_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User response = this.userRepository.save(user);
        return this.userTODto(response);
    }


    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userTODto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
