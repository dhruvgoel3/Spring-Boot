package com.building.blog_app_backend.services;

import com.building.blog_app_backend.dto.CategoryDto;
import com.building.blog_app_backend.entities.Category;
import com.building.blog_app_backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;

    // --------------Here is all out ModelMapper classes---------------
    public CategoryDto userTODto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }


}
