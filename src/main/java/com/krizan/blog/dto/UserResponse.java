package com.krizan.blog.dto;

import com.krizan.blog.model.AppUser;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String username;

    public UserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
    }
}
