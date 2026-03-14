package com.practiceee.practicee.repositories;

import com.practiceee.practicee.entitiy.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserUserId(Long userId);

}
