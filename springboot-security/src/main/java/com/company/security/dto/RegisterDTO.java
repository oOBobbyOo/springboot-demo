package com.company.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Henry
 */
@Data
public class RegisterDTO {

   @NotBlank @Size(min = 3, max = 20, message = "用户名长度3-20")
   private String username;

   @NotBlank
   @Size(min = 6, max = 20, message = "密码长度6-20")
   private String password;
}
