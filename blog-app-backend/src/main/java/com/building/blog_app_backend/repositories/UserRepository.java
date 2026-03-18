package com.building.blog_app_backend.repositories;

import com.building.blog_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
