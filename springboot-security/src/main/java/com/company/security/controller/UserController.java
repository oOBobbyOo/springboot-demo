package com.company.security.controller;

import com.company.security.common.Result;
import com.company.security.entity.User;
import com.company.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

   /**
    * 根据用户ID查询用户详情
    *
    * @param id 用户ID，路径参数
    * @return 统一响应结果
    */
   @GetMapping("/get/{id}")
   public Result<User> getById(@PathVariable Long id) {
      User user = userService.getUserById(id);
      return Result.success(user);
   }
}
