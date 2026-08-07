package com.company.mybatisplus.controller;

import com.company.mybatisplus.common.Result;
import com.company.mybatisplus.entity.User;
import com.company.mybatisplus.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表 前端控制器
 *
 * @author Henry
 * @since 2026-08-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 新增用户
   *
   * @param user 用户信息，请求体参数
   * @return 统一响应结果，新增成功返回成功信息，否则返回失败信息
   */
  @PostMapping("/add")
  public Result<Void> add(@RequestBody User user) {
    boolean success = userService.save(user);
    return success ? Result.success("新增成功") : Result.error("新增失败");
  }

  /**
   * 根据用户 ID 删除用户
   *
   * @param id 用户 ID，路径参数
   * @return 统一响应结果，删除成功返回成功信息，否则返回失败信息
   */
  @DeleteMapping("/delete/{id}")
  public Result<Void> delete(@PathVariable Long id) {
    boolean success = userService.removeById(id);
    return success ? Result.success("删除成功") : Result.error("删除失败");
  }

  /**
   * 批量删除用户
   *
   * <p>根据传入的用户 ID 集合，批量删除对应的用户信息
   *
   * @param ids 用户 ID 集合，请求体参数，例如：[1, 2, 3]
   * @return 批量删除结果提示，成功返回“批量删除成功”，失败返回“批量删除失败”
   */
  @DeleteMapping("/deleteBatch")
  public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
    boolean success = userService.removeByIds(ids);
    return success ? Result.success("批量删除成功") : Result.error("批量删除失败");
  }

  /**
   * 修改用户信息
   *
   * <p>根据用户 ID 更新用户信息，仅更新传入的字段
   *
   * @param user 用户信息，请求体参数，必须包含用户 ID
   * @return 统一响应结果，修改成功返回成功信息，否则返回失败信息
   */
  @PutMapping("/update")
  public Result<Void> update(@RequestBody User user) {
    boolean success = userService.updateById(user);
    return success ? Result.success("修改成功") : Result.error("修改失败");
  }

  /**
   * 根据用户 ID 查询用户详情
   *
   * @param id 用户 ID，路径参数
   * @return 统一响应结果，包含用户详细信息
   */
  @GetMapping("/get/{id}")
  public Result<User> getById(@PathVariable Long id) {
    return Result.success(userService.getById(id));
  }

  /**
   * 查询所有用户列表
   *
   * @return 统一响应结果，包含所有用户信息集合
   */
  @GetMapping("/list")
  public Result<List<User>> list() {
    return Result.success(userService.list());
  }
}
