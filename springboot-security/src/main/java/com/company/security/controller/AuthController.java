package com.company.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
