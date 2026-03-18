package com.building.blog_app_backend.repositories;

import com.building.blog_app_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
