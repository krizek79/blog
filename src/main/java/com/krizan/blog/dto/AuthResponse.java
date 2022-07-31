package com.krizan.blog.dto;

import java.time.Instant;

public record AuthResponse(
        String authenticationToken,
        String refreshToken,
        Instant expiresAt,
        String username
) { }
