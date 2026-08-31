package com.company.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.security.auth.LoginUser;
import com.company.security.entity.User;
import com.company.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security 用户加载器 (UserDetailsService 实现)
 *
 * <p>供 {@code DaoAuthenticationProvider} 在认证时调用：按用户名从数据库加载用户，<br>
 * 并包装为 {@link LoginUser}（{@link UserDetails} 实现），密码校验由 Provider 基于 {@code PasswordEncoder} 完成。
 *
 * @author Henry
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 查询用户 (MyBatis-Plus 会自动追加逻辑删除过滤)
    User user =
        userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, username));

    if (user == null) {
      // 交给 DaoAuthenticationProvider 统一包装为 BadCredentialsException（hideUserNotFoundExceptions 默认开启），
      // 从而与前端的「用户名或密码错误」提示保持一致，防止用户名枚举攻击。
      throw new UsernameNotFoundException("用户不存在: " + username);
    }

    return new LoginUser(user);
  }
}