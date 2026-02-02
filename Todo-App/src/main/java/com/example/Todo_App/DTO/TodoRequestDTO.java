package com.example.Todo_App.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TodoRequestDTO - Used when CLIENT creates or updates a Todo
 * <p>
 * Notice:
 * - No 'id' field (for creation, server generates it)
 * - Has 'userId' field (to know which user this todo belongs to)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "User ID is required")
    private Long userId;  // Which user does this todo belong to?
}