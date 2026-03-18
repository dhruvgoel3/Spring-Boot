package com.building.blog_app_backend.dto;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    private Integer categoryTitle;
    private String categoryDescription;
}