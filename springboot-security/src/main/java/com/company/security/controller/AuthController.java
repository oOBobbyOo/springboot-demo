package com.company.security.controller;

import com.company.security.common.Result;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
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
  public Result<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
    String token = authService.login(dto);
    Map<String, String> data = new HashMap<>();
    data.put("token", token);
    data.put("tokenType", "Bearer");
    return Result.success(data);
  }

  @PostMapping("/register")
  public Result<Void> register(@Valid @RequestBody RegisterDTO dto) {
    authService.register(dto);
    return Result.success();
  }

  @PostMapping("/logout")
  public Result<Void> logout(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      String token = bearerToken.substring(7);
      authService.logout(token);
    }
    return Result.success();
  }
}
