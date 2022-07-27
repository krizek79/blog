package com.krizan.blog.service.user;

import com.krizan.blog.exception.IllegalOperationException;
import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.ConfirmationToken;
import com.krizan.blog.model.AppUser;
import com.krizan.blog.repository.UserRepository;
import com.krizan.blog.service.confirmationToken.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String signUpUser(AppUser appUser) {
        boolean emailTaken = userRepository.findByEmail(appUser.getEmail()).isPresent();
        boolean nicknameTaken = userRepository.findByUsername(appUser.getUsername()).isPresent();
        if (emailTaken || nicknameTaken) throw new IllegalOperationException("Username or email is already taken.");

        String encodedPassword = encoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        userRepository.save(appUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }

    @Override
    public void enableUser(Long id) {
        AppUser appUser = getUserById(id);
        appUser.setEnabled(true);
        appUser.setLocked(false);
        userRepository.save(appUser);
    }

    @Override
    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
    }
}
