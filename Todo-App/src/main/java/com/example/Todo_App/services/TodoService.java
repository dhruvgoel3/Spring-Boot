package com.example.Todo_App.services;

import com.example.Todo_App.DTO.TodoRequestDTO;
import com.example.Todo_App.DTO.TodoResponseDTO;
import com.example.Todo_App.entity.Todo;
import com.example.Todo_App.entity.User;
import com.example.Todo_App.mappers.TodoMapper;
import com.example.Todo_App.repository.TodoRepository;
import com.example.Todo_App.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoMapper todoMapper;

    public TodoResponseDTO createTodo(TodoRequestDTO requestDTO) {
        // First, find the user who owns this todo
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDTO.getUserId()));

        // Convert DTO to Entity
        Todo todo = todoMapper.toEntity(requestDTO, user);

        // Save to database
        Todo savedTodo = todoRepository.save(todo);

        // Convert Entity back to Response DTO
        return todoMapper.toResponseDTO(savedTodo);
    }

    // get  todo by id
    public TodoResponseDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        return todoMapper.toResponseDTO(todo);
    }

    /**
     * Get all todos for a specific user
     * This is very useful! Users only see their own todos
     */
    public List<TodoResponseDTO> getTodosByUserId(Long userId) {
        // Verify user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return todoRepository.findByUserId(userId)
                .stream()
                .map(todoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    /**
     * Update todo
     */
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO requestDTO) {
        // Find existing todo
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Find the user (in case user is being changed)
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + requestDTO.getUserId()));

        // Update entity from DTO
        todoMapper.updateEntityFromDTO(todo, requestDTO, user);

        // Save updated entity
        Todo updatedTodo = todoRepository.save(todo);

        return todoMapper.toResponseDTO(updatedTodo);
    }

    /**
     * Delete todo
     */
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }

        todoRepository.deleteById(id);
    }


}
