package com.krizan.blog.service.user;

import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.AppUser;
import com.krizan.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                appUser.isAccountNonExpired(),
                appUser.isCredentialsNonExpired(),
                appUser.isAccountNonLocked(),
                appUser.getAuthorities()
        );
    }
}
