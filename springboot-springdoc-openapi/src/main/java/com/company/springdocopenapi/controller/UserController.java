package com.company.springdocopenapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public String get() {
        return "Hello World";
    }
}
