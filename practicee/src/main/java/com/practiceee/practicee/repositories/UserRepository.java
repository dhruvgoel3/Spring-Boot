package com.practiceee.practicee.repositories;

import com.practiceee.practicee.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
