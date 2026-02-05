package com.example.Todo_App.controller;
import com.example.Todo_App.DTO.TodoRequestDTO;
import com.example.Todo_App.DTO.TodoResponseDTO;
import com.example.Todo_App.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TodoController - REST API endpoints for Todo operations
 * <p>
 * Base URL: http://localhost:8080/api/todos
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * Create a new todo
     * POST http://localhost:8080/api/todos
     * <p>
     * Request Body (JSON):
     * {
     * "title": "Buy groceries",
     * "description": "Milk, eggs, bread",
     * "userId": 1
     * }
     */
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@Valid @RequestBody TodoRequestDTO requestDTO) {
        TodoResponseDTO response = todoService.createTodo(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get todo by ID
     * GET http://localhost:8080/api/todos/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodoById(@PathVariable Long id) {
        TodoResponseDTO response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all todos for a specific user
     * GET http://localhost:8080/api/todos/user/1
     * <p>
     * This is probably the most-used endpoint!
     * It shows all todos for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponseDTO>> getTodosByUserId(@PathVariable Long userId) {
        List<TodoResponseDTO> response = todoService.getTodosByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all todos (admin view)
     * GET http://localhost:8080/api/todos
     */
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos(@PathVariable Long id) {
        List<TodoResponseDTO> response = todoService.getTodosByUserId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Update todo
     * PUT http://localhost:8080/api/todos/1
     * <p>
     * Request Body (JSON):
     * {
     * "title": "Buy groceries - URGENT",
     * "description": "Milk, eggs, bread, butter",
     * "userId": 1
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDTO requestDTO) {
        TodoResponseDTO response = todoService.updateTodo(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete todo
     * DELETE http://localhost:8080/api/todos/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Exception handler
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}