package com.company.mybatis.controller;

import com.company.mybatis.dto.UserCreateDto;
import com.company.mybatis.dto.UserUpdateDto;
import com.company.mybatis.entity.User;
import com.company.mybatis.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /** 根据 ID 查询用户 GET /api/users/{id} */
  @GetMapping("/{id}")
  public User get(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  /** 查询用户列表 GET /api/users */
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /** 创建用户 POST /api/users */
  @PostMapping()
  public User create(@RequestBody UserCreateDto userCreateDto) {
    return userService.createUser(userCreateDto);
  }

  /** 更新用户 PUT /api/users/{id} */
  @PutMapping("/{id}")
  public User update(
          @PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {

    return userService.updateUser(id, userUpdateDto);
  }

  /** 删除用户 DELETE /api/users/{id} */
  @DeleteMapping("/{id}")
  public int delete(@PathVariable Long id) {
    return userService.deleteUser(id);
  }
}
