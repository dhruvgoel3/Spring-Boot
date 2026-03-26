package com.building.blog_app_backend.dto;

import com.building.blog_app_backend.entities.Category;
import com.building.blog_app_backend.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class PostDto {

    private String postTitle;
    private String postContent;
    private String imageName;
    private Date addedDate;
    private Category category;
    private User user;
}
