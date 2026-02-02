package com.example.Todo_App.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TodoResponseDTO - Used when SERVER sends Todo data TO the client
 *
 * Notice:
 * - Has 'id' field (to identify the todo)
 * - Has 'userId' field (to show which user owns it)
 * - Optionally could include 'username' if we want to show user info too
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String username;  // Bonus: showing the owner's username
}