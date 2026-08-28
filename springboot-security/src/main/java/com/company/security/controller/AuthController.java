package com.company.security.controller;

import com.company.security.common.Result;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public Result<Void> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success();
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO dto) {
        return Result.success();
    }
}
