package com.krizan.blog.service.user;

import com.krizan.blog.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    String signUpUser(AppUser appUser);
    void enableUser(Long id);
    AppUser getUserById(Long id);
    List<AppUser> getAllUsers();
}
