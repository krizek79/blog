package com.krizan.blog.controller;

import com.krizan.blog.dto.AppUserRegistrationRequest;
import com.krizan.blog.dto.AuthResponse;
import com.krizan.blog.dto.LoginRequest;
import com.krizan.blog.dto.RefreshTokenRequest;
import com.krizan.blog.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public record AuthController(AuthService authService) {

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody AppUserRegistrationRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return new ResponseEntity<>(authService.confirmConfirmationToken(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return new ResponseEntity<>(authService.refreshToken(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        return new ResponseEntity<>(authService.logout(request), HttpStatus.OK);
    }
}
