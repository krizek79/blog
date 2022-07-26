package com.krizan.blog.service;

import com.krizan.blog.dto.UserRegistrationRequest;
import com.krizan.blog.exception.IllegalOperationException;
import com.krizan.blog.model.AppUser;
import com.krizan.blog.model.ConfirmationToken;
import com.krizan.blog.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Override
    public String registerUser(UserRegistrationRequest request) {
        Boolean isEmailValid = emailService.validateEmail(request.email());
        if (Boolean.FALSE.equals(isEmailValid))
            throw new IllegalOperationException("Email is not valid.");

        if (!request.password().equals(request.repeatPassword()))
            throw new IllegalOperationException("Passwords do not match.");

        AppUser appUser = AppUser.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .build();

        String token = userService.signUpUser(appUser);
        String confirmationLink = "http://localhost:8080/registration/confirm?token=" + token;
//        EmailRequest emailRequest = new EmailRequest(request.email(), buildEmail(request.userName(), confirmationLink));
        log.info("Confirm registration here: " + confirmationLink);

        return token;
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getAppUser().getId());
        return "confirmed";
    }
}
