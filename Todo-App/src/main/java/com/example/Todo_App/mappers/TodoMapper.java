package com.example.Todo_App.mappers;


import com.example.Todo_App.DTO.TodoRequestDTO;
import com.example.Todo_App.DTO.TodoResponseDTO;
import com.example.Todo_App.entity.Todo;
import com.example.Todo_App.entity.User;
import org.springframework.stereotype.Component;

/**
 * TodoMapper - Converts between Todo Entity and DTOs
 */
@Component
public class TodoMapper {

    /**
     * Convert TodoRequestDTO to Todo Entity
     * <p>
     * Note: We need the User object to set the relationship
     */
    public Todo toEntity(TodoRequestDTO dto, User user) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setUser(user);  // Setting the relationship
        return todo;
    }

    /**
     * Convert Todo Entity to TodoResponseDTO
     */
    public TodoResponseDTO toResponseDTO(Todo todo) {
        TodoResponseDTO dto = new TodoResponseDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setUserId(todo.getUser().getId());
        dto.setUsername(todo.getUser().getUsername());  // Bonus: include username
        return dto;
    }

    /**
     * Update existing Todo entity from DTO
     */
    public void updateEntityFromDTO(Todo todo, TodoRequestDTO dto, User user) {
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setUser(user);  // Update the relationship if needed
    }
}