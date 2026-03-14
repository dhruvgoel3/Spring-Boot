package com.practiceee.practicee.controllers;

import com.practiceee.practicee.entitiy.Todo;
import com.practiceee.practicee.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/{userId}/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo, @PathVariable Long userId) {

        Todo response = todoService.createTodo(todo, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long userId, @PathVariable Long todoId, @RequestBody Todo todo) {

        Todo response = todoService.updateTodo(userId, todoId, todo);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodosOfUser(@PathVariable Long userId) {
        List<Todo> response = todoService.getAllTodosOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoByTodoId(@PathVariable Long userId, @PathVariable Long todoId) {

        Todo response = todoService.getTodoById(todoId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long userId, @PathVariable Long todoId) {

        todoService.deleteById(userId, todoId);
        return ResponseEntity.ok("Todo deleted successfully");
    }
}