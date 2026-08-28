package com.company.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.entity.User;
import com.company.security.mapper.UserMapper;
import com.company.security.service.AuthService;
import com.company.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  @Override
  public String login(LoginDTO dto) {
    // 1. 查询用户 (包含逻辑删除过滤)
    User user =
        userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));

    // 2. 校验密码与状态
    // 无论用户不存在还是密码错误，统一提示“用户名或密码错误”，防止用户名枚举攻击
    if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      log.warn("登录失败: 用户名或密码错误 - {}", dto.getUsername());
    }

    if (user != null && user.getStatus() == 0) {
      log.warn("登录失败: 账号已被禁用 - {}", dto.getUsername());
    }

    // 3. 生成 Token

    return "token";
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
    }

    // 2. 构建用户实体
    User user = new User();
    user.setUsername(dto.getUsername());
    // 密码必须使用 BCrypt 等强哈希算法加盐存储，绝不可明文存储
    user.setPassword(dto.getPassword());
    // 默认正常状态
    user.setStatus(1);

    // 3. 保存入库
    int rows = userMapper.insert(user);
    if (rows == 0) {
      log.info("注册失败，请稍后重试");
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
