package com.building.blog_app_backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "title")
    private String categoryTitle;
    @Column
    private String categoryDescription;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
