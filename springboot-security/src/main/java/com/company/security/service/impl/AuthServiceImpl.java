package com.company.security.service.impl;

import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.entity.User;
import com.company.security.mapper.UserMapper;
import com.company.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

   @Override
   public String login(LoginDTO dto) {
      return  "token";
   }

   @Override
   @Transactional(rollbackFor = Exception.class)
   public void register(RegisterDTO dto) {

      log.info(String.valueOf(dto));

      // 2. 构建用户实体
      User user = new User();
      user.setUsername(dto.getUsername());
      // 密码必须使用 BCrypt 等强哈希算法加盐存储，绝不可明文存储
      user.setPassword(dto.getPassword());
      // 默认正常状态
      user.setStatus(1);

      // 3. 保存入库
      userMapper.insert(user);

      log.info("新用户注册成功: {}", dto.getUsername());
   }

   @Override
   public void logout(String token) {

   }
}
