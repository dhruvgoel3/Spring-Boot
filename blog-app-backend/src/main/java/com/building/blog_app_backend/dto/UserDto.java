package com.building.blog_app_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 5, message = "Username must be of atleast 20 characters")
    private String name;
    @Email
    @Size(min = 20, message = "Email must be of atleast 20 characters")
    private String email;
    @NotEmpty
    @Size(min = 6,max = 10,message = "Password enter the password of required format ")
    private String password;
    @NotEmpty
    private String about;
}
