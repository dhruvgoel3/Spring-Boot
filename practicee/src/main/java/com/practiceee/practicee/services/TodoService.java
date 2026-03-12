package com.practiceee.practicee.services;

import com.practiceee.practicee.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    @Autowired
    private final TodoRepository todoRepository;
}
