package com.company.security.controller;

import com.company.security.common.Result;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Void> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success();
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO dto) {
        System.out.print(dto);

        authService.register(dto);
        return Result.success();
    }
}
