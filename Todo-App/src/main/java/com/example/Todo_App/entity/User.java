package com.example.Todo_App.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor  // Lombok: generates no-args constructor
@AllArgsConstructor  // Lombok: generates all-args constructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;  // In real apps, this should be encrypted!

    /**
     * One user can have many todos
     * mappedBy = "user" means the Todo entity has a field called "user" that owns this relationship
     * cascade = ALL means when we delete a user, delete all their todos too
     * orphanRemoval = true means if a todo is removed from this list, delete it from database
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();
}