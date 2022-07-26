package com.krizan.blog.controller;

import com.krizan.blog.dto.UserRegistrationRequest;
import com.krizan.blog.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public record RegistrationController(RegistrationService registrationService) {

    @PostMapping
    public String register(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
