package com.project.Fitness_Tracker.repository;

import com.project.Fitness_Tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
