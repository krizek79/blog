package com.krizan.blog.service;

import com.krizan.blog.dto.PostCreationRequest;
import com.krizan.blog.dto.PostUpdateRequest;
import com.krizan.blog.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(PostCreationRequest request);
    Post updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
    Post getPostById(Long id);
    List<Post> getAllPosts();
    List<Post> getAllPostsByUserId(Long id);
}
