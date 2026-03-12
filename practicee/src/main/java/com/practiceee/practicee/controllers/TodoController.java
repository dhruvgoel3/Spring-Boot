package com.practiceee.practicee.controllers;

import com.practiceee.practicee.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoController {
    @Autowired
    private final TodoService todoService;
}
