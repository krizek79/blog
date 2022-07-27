package com.krizan.blog.service.registration;

import com.krizan.blog.dto.AppUserRegistrationRequest;
import jakarta.transaction.Transactional;

public interface RegistrationService {

    String registerUser(AppUserRegistrationRequest request);
    @Transactional
    String confirmToken(String token);
}
