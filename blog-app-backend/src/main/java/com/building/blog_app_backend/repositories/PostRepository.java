package com.building.blog_app_backend.repositories;

import com.building.blog_app_backend.dto.PostDto;
import com.building.blog_app_backend.entities.Category;
import com.building.blog_app_backend.entities.Post;
import com.building.blog_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);
}
