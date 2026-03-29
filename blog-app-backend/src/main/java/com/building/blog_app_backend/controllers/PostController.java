package com.building.blog_app_backend.controllers;

import com.building.blog_app_backend.dto.PostDto;
import com.building.blog_app_backend.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostByCategory(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

    }

}
