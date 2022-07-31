package com.krizan.blog.dto;

public record RefreshTokenRequest(
        String refreshToken,
        String username
) { }
