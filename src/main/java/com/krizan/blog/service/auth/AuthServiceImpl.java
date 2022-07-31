package com.krizan.blog.service.auth;

import com.krizan.blog.dto.AppUserRegistrationRequest;
import com.krizan.blog.dto.AuthResponse;
import com.krizan.blog.dto.LoginRequest;
import com.krizan.blog.dto.RefreshTokenRequest;
import com.krizan.blog.exception.IllegalOperationException;
import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.AppUser;
import com.krizan.blog.model.ConfirmationToken;
import com.krizan.blog.model.Role;
import com.krizan.blog.repository.UserRepository;
import com.krizan.blog.security.JwtProvider;
import com.krizan.blog.service.confirmationToken.ConfirmationTokenService;
import com.krizan.blog.service.email.EmailService;
import com.krizan.blog.service.refreshToken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public String register(AppUserRegistrationRequest request) {
        Boolean isEmailValid = emailService.validateEmail(request.email());
        if (Boolean.FALSE.equals(isEmailValid))
            throw new IllegalOperationException("Email is not valid.");

        if (!request.password().equals(request.repeatPassword()))
            throw new IllegalOperationException("Passwords do not match.");

        AppUser appUser = AppUser.builder()
                .username(request.username())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.USER)
                .locked(true)
                .enabled(false)
                .build();

        boolean emailTaken = userRepository.findByEmail(appUser.getEmail()).isPresent();
        boolean nicknameTaken = userRepository.findByUsername(appUser.getUsername()).isPresent();
        if (emailTaken || nicknameTaken) throw new IllegalOperationException("Username or email is already taken.");

        userRepository.save(appUser);

        String token = generateConfirmationToken(appUser);
        String confirmationLink = "http://localhost:8080/auth/confirm?token=" + token;
        log.info("Confirm registration here: " + confirmationLink);

        return "Please check your email for confirmation link.";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthResponse(
                token,
                refreshTokenService.generateRefreshToken().getToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
                request.username()
        );
    }

    @Override
    public AppUser getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject()).orElseThrow(NotFoundException::new);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        refreshTokenService.validateRefreshToken(request.refreshToken());
        String token = jwtProvider.generateTokenWithUsername(request.username());
        return new AuthResponse(
                token,
                request.refreshToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
                request.username()
        );
    }

    @Override
    public String confirmConfirmationToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);

        AppUser appUser = confirmationToken.getAppUser();
        appUser.setEnabled(true);
        appUser.setLocked(false);
        userRepository.save(appUser);

        return "Registration complete";
    }

    private String generateConfirmationToken(AppUser appUser) {
        ConfirmationToken confirmationToken = new ConfirmationToken(appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }

    @Override
    public String logout(RefreshTokenRequest request) {
        refreshTokenService.deleteRefreshToken(request.refreshToken());
        return "Logout successful";
    }
}
