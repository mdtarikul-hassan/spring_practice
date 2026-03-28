package com.oauth.controller;

import com.oauth.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @GetMapping
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "tarik"));
        users.add(new User("2", "tarik1"));
        users.add(new User("3", "tarik2"));
        users.add(new User("4", "tarik3"));
        users.add(new User("5", "tarik4"));
        users.add(new User("6", "tarik5"));
        users.add(new User("7", "tarik6"));
        users.add(new User("8", "tarik7"));
        return users;
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        return "user created";
    }
}
