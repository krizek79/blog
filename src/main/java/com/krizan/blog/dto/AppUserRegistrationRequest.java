package com.krizan.blog.dto;

public record AppUserRegistrationRequest(
    String username,
    String email,
    String password,
    String repeatPassword
) { }
