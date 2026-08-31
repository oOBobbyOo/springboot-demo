package com.company.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.security.auth.LoginUser;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.entity.User;
import com.company.security.exception.BusinessException;
import com.company.security.mapper.UserMapper;
import com.company.security.service.AuthService;
import com.company.security.util.JwtUtils;
import com.company.security.vo.LoginVO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Henry
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  @Override
  public LoginVO login(LoginDTO dto) {
    try {
      // 1. 触发 Spring Security 标准认证流程：
      // 由 DaoAuthenticationProvider 基于 UserDetailsService 加载用户、
      // 使用 PasswordEncoder 校验密码，并自动执行 UserDetails.isEnabled() 等账号状态校验。
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

      // 2. 认证通过，取出 principal (UserDetailsService 返回的 LoginUser)
      LoginUser loginUser = (LoginUser) authentication.getPrincipal();

      // 3. 生成双 Token
      // subject 存放 username，业务数据 (userId) 放入 Access Token 的额外 Claims 中。
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("userId", loginUser.getUser().getId());

      String accessToken = jwtUtils.generateToken(loginUser, extraClaims);
      String refreshToken = jwtUtils.generateRefreshToken(loginUser);

      log.info("登录成功: {}", dto.getUsername());
      return new LoginVO(accessToken, refreshToken);
    } catch (BadCredentialsException e) {
      // 无论用户不存在还是密码错误，DaoAuthenticationProvider 统一抛出 BadCredentialsException，
      // 统一提示「用户名或密码错误」，防止用户名枚举攻击
      log.warn("登录失败: 用户名或密码错误 - {}", dto.getUsername());
      throw new BusinessException(401, "用户名或密码错误");
    } catch (DisabledException e) {
      // 账号状态校验失败 (isEnabled() 返回 false，即 status=0)
      log.warn("登录失败: 账号已被禁用 - {}", dto.getUsername());
      throw new BusinessException(403, "账号已被禁用，请联系管理员");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void register(RegisterDTO dto) {
    // 1. 校验用户名唯一性
    Long count =
        userMapper.selectCount(
            new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
    if (count > 0) {
      log.info("用户名已存在: {}", dto.getUsername());
      throw new BusinessException(400, "用户名已存在");
    }

    // 2. 构建用户实体
    User user = new User();
    user.setUsername(dto.getUsername());
    // 密码必须使用 BCrypt 等强哈希算法加盐存储，绝不可明文存储
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    // 默认正常状态
    user.setStatus(1);

    // 3. 保存入库
    int rows = userMapper.insert(user);
    if (rows == 0) {
      log.info("注册失败，请稍后重试");
      throw new BusinessException(500, "注册失败，请稍后重试");
    }
    log.info("新用户注册成功: {}", dto.getUsername());
  }

  @Override
  public void logout(String token) {
    if (token == null || token.isEmpty()) {
      return;
    }

    log.info("用户登出");
  }
}
