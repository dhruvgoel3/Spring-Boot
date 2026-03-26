package com.building.blog_app_backend.controllers;

import com.building.blog_app_backend.dto.CategoryDto;
import com.building.blog_app_backend.services.CategoryService;
import com.building.blog_app_backend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);

    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer catId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(updatedCategory);

    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        categoryService.deleteCategoryById(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted Sucessfully !!", true), HttpStatus.OK);

    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
        CategoryDto category = categoryService.getCategory(catId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Get Categories
}
