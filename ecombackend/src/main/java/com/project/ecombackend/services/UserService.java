package com.project.ecombackend.services;

import com.project.ecombackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();
    private long nextId = 1L;


    public List<User> fetchAllUser() {
        return userList;
    }

    public User fetchOneUser(Long userId) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public List<User> createUsers(User user) {
        user.setId(nextId++);
        userList.add(user);
        return userList;


    }

    public boolean updateUser(Long userId, User updatedUser) {
        return userList.stream().filter(user -> user.getId().equals(userId)).findFirst().map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            return true;

        }).orElse(false);
    }

}
