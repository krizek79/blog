package com.krizan.blog.dto;

public record PostCreationRequest(
        Long userId,
        String title,
        String body
) { }
