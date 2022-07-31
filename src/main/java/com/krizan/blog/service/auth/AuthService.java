package com.krizan.blog.service.auth;

import com.krizan.blog.dto.AppUserRegistrationRequest;
import com.krizan.blog.dto.AuthResponse;
import com.krizan.blog.dto.LoginRequest;
import com.krizan.blog.dto.RefreshTokenRequest;
import com.krizan.blog.model.AppUser;
import jakarta.transaction.Transactional;

public interface AuthService {

    String register(AppUserRegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AppUser getCurrentUser();
    AuthResponse refreshToken(RefreshTokenRequest request);
    @Transactional
    String confirmConfirmationToken(String token);
    String logout(RefreshTokenRequest request);
}
