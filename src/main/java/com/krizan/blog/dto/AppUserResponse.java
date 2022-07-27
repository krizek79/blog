package com.krizan.blog.dto;

import com.krizan.blog.model.AppUser;
import lombok.Getter;

@Getter
public class AppUserResponse {

    private final Long id;
    private final String username;
    private final String email;

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
    }
}
