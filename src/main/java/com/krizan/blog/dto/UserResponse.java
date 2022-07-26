package com.krizan.blog.dto;

import com.krizan.blog.model.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String username;

    public UserResponse(User user) {
        username = user.getUsername();
    }
}
