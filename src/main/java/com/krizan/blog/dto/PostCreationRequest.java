package com.krizan.blog.dto;

public record PostCreationRequest(
        String title,
        String body
) { }
