package com.company.mybatisplus.controller;

import com.company.mybatisplus.common.Result;
import com.company.mybatisplus.entity.Role;
import com.company.mybatisplus.service.RoleService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表 前端控制器
 *
 * @author Henry
 * @since 2026-08-07
 */
@RestController
@RequestMapping("/role")
public class RoleController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  /**
   * 新增角色
   *
   * @param role 角色信息
   * @return 操作结果
   */
  @PostMapping("/add")
  public Result<Void> save(@RequestBody Role role) {
    boolean success = roleService.save(role);
    return success ? Result.success("新增角色成功") : Result.error("新增角色失败");
  }

  /**
   * 根据角色ID删除角色
   *
   * @param id 角色ID
   * @return 操作结果
   */
  @DeleteMapping("/delete/{id}")
  public Result<Void> delete(@PathVariable Long id) {
    boolean success = roleService.removeById(id);
    return success ? Result.success("删除角色成功") : Result.error("删除角色失败");
  }

  /**
   * 批量删除角色
   *
   * <p>根据传入的角色 ID 集合，批量删除对应的角色信息
   *
   * @param ids 角色 ID 集合，请求体参数，例如：[1, 2, 3]
   * @return 批量删除结果提示，成功返回“批量删除成功”，失败返回“批量删除失败”
   */
  @DeleteMapping("/deleteBatch")
  public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
    boolean success = roleService.removeByIds(ids);
    return success ? Result.success("批量删除成功") : Result.error("批量删除失败");
  }

  /**
   * 修改角色信息
   *
   * @param role 角色信息，必须包含角色ID
   * @return 操作结果
   */
  @PutMapping("/update")
  public Result<Void> update(@RequestBody Role role) {
    boolean success = roleService.updateById(role);
    return success ? Result.success("修改角色成功") : Result.error("修改角色失败");
  }

  /**
   * 根据角色ID查询角色详情
   *
   * @param id 角色ID
   * @return 角色详情
   */
  @GetMapping("/get/{id}")
  public Result<Role> getById(@PathVariable Long id) {
    Role role = roleService.getById(id);
    return Result.success(role);
  }

  /**
   * 查询所有角色列表
   *
   * @return 角色列表
   */
  @GetMapping("/list")
  public Result<List<Role>> list() {
    List<Role> list = roleService.list();
    return Result.success(list);
  }
}
