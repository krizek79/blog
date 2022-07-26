package com.krizan.blog.dto;

import com.krizan.blog.vo.UserWithPosts;
import lombok.Getter;

import java.util.List;

@Getter
public class UserWithPostsResponse {

    private final String username;
    private final List<PostResponse> posts;

    public UserWithPostsResponse(UserWithPosts userWithPosts) {
        this.username = userWithPosts.user().getUsername();
        this.posts = userWithPosts.posts();
    }
}
