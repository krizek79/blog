package com.krizan.blog.controller;

import com.krizan.blog.dto.AppUserResponse;
import com.krizan.blog.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public record UserController(UserService userService) {

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> getAppUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new AppUserResponse(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping
    public List<AppUserResponse> getAllAppUsers() {
        return userService.getAllUsers()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }
}
