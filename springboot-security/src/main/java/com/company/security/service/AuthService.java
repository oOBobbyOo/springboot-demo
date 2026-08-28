package com.company.security.service;

import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.vo.LoginVO;

/**
 * 认证服务接口
 *
 * @author Henry
 */
public interface AuthService {

   /**
    * 用户登录
    * @param dto 登录参数
    * @return 双 Token (Access Token + Refresh Token)
    */
   LoginVO login(LoginDTO dto);


   /**
    * 用户注册
    * @param dto 注册参数
    */
   void register(RegisterDTO dto);

   /**
    * 用户登出
    * @param token 当前请求头中的 Bearer Token
    */
   void logout(String token);
}
