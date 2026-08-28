package com.company.security.vo;

import lombok.Data;

/**
 * 登录响应 VO (双 Token)
 *
 * @author Henry
 */
@Data
public class LoginVO {

   /**
    * 访问令牌 (短期有效，用于业务接口鉴权)
    */
   private String accessToken;

   /**
    * 刷新令牌 (长期有效，用于换取新的 Access Token)
    */
   private String refreshToken;

   /**
    * Token 类型 (固定为 Bearer，前端拼接 Authorization 请求头)
    */
   private String tokenType = "Bearer";

   public LoginVO(String accessToken, String refreshToken) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }
}