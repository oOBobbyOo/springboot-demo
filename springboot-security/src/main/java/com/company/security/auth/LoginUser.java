package com.company.security.auth;

import com.company.security.entity.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security 登录用户 (UserDetails 实现)
 *
 * <p>与数据库实体 {@link User} 解耦：User 只负责持久化，LoginUser 只负责认证/授权。
 * 后续 JwtAuthenticationFilter 从 Token 解析出 username 后，可查询 User 并包装成此类放入 SecurityContext。
 *
 * @author Henry
 */
public class LoginUser implements UserDetails {

   /** 底层用户实体 */
   private final User user;

   public LoginUser(User user) {
      this.user = user;
   }

   /** 获取底层用户实体 (供业务层读取 userId、nickname 等字段) */
   public User getUser() {
      return user;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      // 当前无 RBAC 角色/权限体系，返回空集合；后续可扩展为从角色表加载。
      return Collections.emptyList();
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      // status: 0:停用, 1:正常 (与 login 校验、register 默认值保持一致)
      return Integer.valueOf(1).equals(user.getStatus());
   }
}