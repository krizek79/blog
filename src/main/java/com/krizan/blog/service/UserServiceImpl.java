package com.krizan.blog.service;

import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.User;
import com.krizan.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostService postService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String signUpUser(User user) {
        return null;
    }

    @Override
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
    }
}
