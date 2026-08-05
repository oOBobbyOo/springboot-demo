package com.company.mybatis.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Henry
 */
@Data
public class User {
  private Long id;
  private String userName;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
