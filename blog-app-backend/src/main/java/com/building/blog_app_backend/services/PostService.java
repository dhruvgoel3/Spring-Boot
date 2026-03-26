package com.building.blog_app_backend.services;

import com.building.blog_app_backend.dto.PostDto;
import com.building.blog_app_backend.entities.Category;
import com.building.blog_app_backend.entities.Post;
import com.building.blog_app_backend.entities.User;
import com.building.blog_app_backend.exceptions.ResourceNotFoundException;
import com.building.blog_app_backend.repositories.CategoryRepository;
import com.building.blog_app_backend.repositories.PostRepository;
import com.building.blog_app_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService {
    private final ModelMapper modelMapper;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = postRepository.save(post);
        return this.modelMapper.map(newPost, Post.class);
    }

//    private PostDto updatePost(PostDto postDto, Integer postId) {
//
//    }
//
//    public List<PostDto> getAllPost() {
//
//    }
//
//    public List<PostDto> getPostByCategory(Integer categoryId) {
//
//    }
//
//    public List<PostDto> getPostByUser(Integer userId) {
//
//    }
}
