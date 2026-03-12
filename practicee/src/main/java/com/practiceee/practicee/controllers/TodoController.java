package com.practiceee.practicee.controllers;

import com.practiceee.practicee.entitiy.Todo;
import com.practiceee.practicee.entitiy.User;
import com.practiceee.practicee.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoController {
    @Autowired
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo response = todoService.createTodo(todo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Todo response = todoService.updateTodo(id, todo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllTodo")
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> response = todoService.getAllTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Todo>> getTodoById(@PathVariable Long id) {
        Optional<Todo> response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteById(id);
    }


}
