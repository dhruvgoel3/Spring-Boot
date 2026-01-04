package com.example.Todo_App.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    private Long profileId;
    private String gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
