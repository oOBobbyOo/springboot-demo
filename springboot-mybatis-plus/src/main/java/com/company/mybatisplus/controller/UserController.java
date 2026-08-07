package com.company.mybatisplus.controller;

import com.company.mybatisplus.entity.User;
import com.company.mybatisplus.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表 前端控制器
 *
 * @author henry
 * @since 2026-08-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /** 新增用户 */
  @PostMapping("/add")
  public String add(@RequestBody User user) {
    boolean success = userService.save(user);
    return success ? "新增成功" : "新增失败";
  }

  /** 根据 ID 删除用户 */
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    boolean success = userService.removeById(id);
    return success ? "删除成功" : "删除失败";
  }

  /** 批量删除用户 */
  @DeleteMapping("/deleteBatch")
  public String deleteBatch(@RequestBody List<Long> ids) {
    boolean success = userService.removeByIds(ids);
    return success ? "批量删除成功" : "批量删除失败";
  }

  /** 修改用户 */
  @PutMapping("/update")
  public String update(@RequestBody User user) {
    boolean success = userService.updateById(user);
    return success ? "修改成功" : "修改失败";
  }

  /** 根据 ID 查询用户 */
  @GetMapping("/get/{id}")
  public User getById(@PathVariable Long id) {
    return userService.getById(id);
  }

  /** 查询全部用户 */
  @GetMapping("/list")
  public List<User> list() {
    return userService.list();
  }
}
