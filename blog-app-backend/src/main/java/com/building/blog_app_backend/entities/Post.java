package com.building.blog_app_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Date;

@Entity
@Data
@Table(name = "posts")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String imageName;
    private Date addDate;

    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;


}
