package com.accesspoint.controller;

import com.accesspoint.io.ProfileRequest;
import com.accesspoint.io.ProfileResponse;
import com.accesspoint.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse profile = userService.createProfile(request);
        return profile;
    }
}
