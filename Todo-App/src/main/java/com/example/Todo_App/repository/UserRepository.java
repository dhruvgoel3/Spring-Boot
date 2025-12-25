package com.example.Todo_App.repository;

import com.example.Todo_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
