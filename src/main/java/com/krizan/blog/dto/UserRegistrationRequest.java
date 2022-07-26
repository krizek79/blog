package com.krizan.blog.dto;

public record UserRegistrationRequest(
    String username,
    String email,
    String password,
    String repeatPassword
) { }
