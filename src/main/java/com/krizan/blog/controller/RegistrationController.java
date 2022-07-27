package com.krizan.blog.controller;

import com.krizan.blog.dto.AppUserRegistrationRequest;
import com.krizan.blog.service.registration.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public record RegistrationController(RegistrationService registrationService) {

    @PostMapping
    public String register(@RequestBody AppUserRegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
