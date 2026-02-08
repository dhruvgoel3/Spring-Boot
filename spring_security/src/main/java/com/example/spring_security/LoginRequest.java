package com.example.spring_security;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

}
