package com.krizan.blog.dto;

public record LoginRequest(
        String username,
        String password
) {
}
