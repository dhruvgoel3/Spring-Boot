package com.example.Todo_App.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserResponseDTO - Used when SERVER sends data TO the client
 *
 * Use Case: Returning user info (GET /api/users/{id})
 *
 * Notice:
 * - Has 'id' field (to identify the user)
 * - NO 'password' field (SECURITY! Never send passwords to client)
 * - Only the safe, public information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    // NO PASSWORD HERE! This is the key benefit of DTOs
}