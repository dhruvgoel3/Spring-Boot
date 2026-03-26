package com.building.blog_app_backend.services;

import com.building.blog_app_backend.dto.CategoryDto;
import com.building.blog_app_backend.entities.Category;
import com.building.blog_app_backend.exceptions.ResourceNotFoundException;
import com.building.blog_app_backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;


    // Create Category
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    // update Category
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    public void deleteCategoryById(Integer catID) {
        Category cat = categoryRepository.findById(catID).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", catID));
        categoryRepository.delete(cat);
    }

    public CategoryDto getCategory(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", catId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categoryDtos = categoryRepository.findAll();
        List<CategoryDto> catDto = categoryDtos.stream().map((cat) -> this.modelMapper.map(categoryDtos, CategoryDto.class)).collect(Collectors.toList());
        return catDto;
    }


    // --------------Here is all out ModelMapper classes---------------
    public CategoryDto entityTODto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    public Category dtoToEntity(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;

    }


}
