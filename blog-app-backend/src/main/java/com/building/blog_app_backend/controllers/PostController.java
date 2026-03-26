package com.building.blog_app_backend.controllers;

import com.building.blog_app_backend.dto.PostDto;
import com.building.blog_app_backend.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

}
