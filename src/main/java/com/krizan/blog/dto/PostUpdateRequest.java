package com.krizan.blog.dto;

public record PostUpdateRequest(
    String title,
    String body
) { }
