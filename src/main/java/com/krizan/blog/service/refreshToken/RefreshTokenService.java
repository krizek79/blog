package com.krizan.blog.service.refreshToken;

import com.krizan.blog.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    void deleteRefreshToken(String token);
}
