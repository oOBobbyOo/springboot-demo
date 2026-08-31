package com.company.security.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Henry
 */
@Data
public class UserVO {
   private Long id;
   private String username;
   private String nickname;
   private String email;
   private String phone;
   private String avatar;
   private Integer gender;
   private String remark;
   private LocalDateTime createTime;
}
