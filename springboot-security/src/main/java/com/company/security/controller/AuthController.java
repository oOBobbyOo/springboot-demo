package com.company.security.controller;

import com.company.security.common.Result;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.service.AuthService;
import com.company.security.vo.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
  public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
    return Result.success(authService.login(dto));
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
