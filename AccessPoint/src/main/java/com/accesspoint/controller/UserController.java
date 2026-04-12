package com.accesspoint.controller;

import com.accesspoint.io.ProfileRequest;
import com.accesspoint.io.ProfileResponse;
import com.accesspoint.service.EmailService;
import com.accesspoint.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;
    private EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = userService.createProfile(request);
        emailService.sendWelcomeEmail(response.getEmail(), response.getName());

        return response;
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email){
        ProfileResponse profile = userService.getProfile(email);


        return profile;
    }
}
