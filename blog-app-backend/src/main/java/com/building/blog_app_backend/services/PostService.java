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
import java.util.List;
import java.util.stream.Collectors;

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
        return this.modelMapper.map(newPost, PostDto.class);
    }

    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        List<Post> posts = postRepository.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }

    public List<PostDto> getPostByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        List<Post> posts = postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    public List<PostDto> getAllPost() {
        List<Post> allPosts = postRepository.findAll();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    public PostDto getPostById(Integer postId)
    {
        Post postDto = postRepository.findById(postId);
    }
}
