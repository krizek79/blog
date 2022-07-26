package com.krizan.blog.service;

import com.krizan.blog.model.ConfirmationToken;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);
    ConfirmationToken getToken(String token);
    void setConfirmedAt(String token);
}
