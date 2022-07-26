package com.krizan.blog.service;

import com.krizan.blog.dto.PostCreationRequest;
import com.krizan.blog.dto.PostUpdateRequest;
import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.Post;
import com.krizan.blog.model.User;
import com.krizan.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Post createPost(PostCreationRequest request) {
        User user = userService.getUserById(request.userId());
        Post post = Post.builder()
                .user(user)
                .title(request.title())
                .body(request.body())
                .createdAt(LocalDateTime.now())
                .build();
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, PostUpdateRequest request) {
        Post post = getPostById(id);
        post.setTitle(request.title());
        post.setBody(request.body());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }
}
