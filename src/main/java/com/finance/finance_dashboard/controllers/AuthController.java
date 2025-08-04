package com.finance.finance_dashboard.controllers;

import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Basic validation can be added
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}