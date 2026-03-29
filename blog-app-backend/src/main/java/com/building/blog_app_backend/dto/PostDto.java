package com.building.blog_app_backend.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class PostDto {

    private String postTitle;
    private String postContent;
    private String imageName;
    private Date addDate;
    private CategoryDto category;
    private UserDto user;
}
