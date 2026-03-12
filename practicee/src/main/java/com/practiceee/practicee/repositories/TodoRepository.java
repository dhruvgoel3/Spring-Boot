package com.practiceee.practicee.repositories;

import com.practiceee.practicee.entitiy.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
