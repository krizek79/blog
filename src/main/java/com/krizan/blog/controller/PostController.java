package com.krizan.blog.controller;

import com.krizan.blog.dto.PostCreationRequest;
import com.krizan.blog.dto.PostResponse;
import com.krizan.blog.dto.PostUpdateRequest;
import com.krizan.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public record PostController(PostService postService) {

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreationRequest request) {
        return new ResponseEntity<>(new PostResponse(postService.createPost(request)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdateRequest request) {
        return new ResponseEntity<>(new PostResponse(postService.updatePost(id, request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts()
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new PostResponse(postService.getPostById(id)), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public List<PostResponse> getAllPostsByUserId(@PathVariable("id") Long id) {
        return postService.getAllPostsByUserId(id)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
