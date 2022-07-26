package com.krizan.blog.service;

import com.krizan.blog.dto.UserRegistrationRequest;
import jakarta.transaction.Transactional;

public interface RegistrationService {

    String registerUser(UserRegistrationRequest request);
    @Transactional
    String confirmToken(String token);
}
