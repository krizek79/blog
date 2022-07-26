package com.krizan.blog.service;

import com.krizan.blog.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String signUpUser(User user);
    void enableUser(String email);
    User getUserById(Long id);
}
