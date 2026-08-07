package com.company.mybatisplus.controller;

import com.company.mybatisplus.entity.Role;
import com.company.mybatisplus.service.RoleService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表 前端控制器
 *
 * @author henry
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
   */
  @PostMapping("/add")
  public String add(@RequestBody Role role) {
    boolean success = roleService.save(role);
    return success ? "新增成功" : "新增失败";
  }

  /**
   * 根据ID删除角色
   *
   * @param id 角色id
   */
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    boolean success = roleService.removeById(id);
    return success ? "删除成功" : "删除失败";
  }

  /**
   * 批量删除角色
   *
   * @param ids 角色id列表，例如 [1, 2]
   */
  @DeleteMapping("/deleteBatch")
  public String deleteBatch(@RequestBody List<Long> ids) {
    boolean success = roleService.removeByIds(ids);
    return success ? "批量删除成功" : "批量删除失败";
  }

  /**
   * 修改角色
   *
   * @param role 角色信息
   */
  @PutMapping("/update")
  public String update(@RequestBody Role role) {
    boolean success = roleService.updateById(role);
    return success ? "修改成功" : "修改失败";
  }

  /**
   * 根据ID查询角色
   *
   * @param id 角色id
   */
  @GetMapping("/get/{id}")
  public Role getById(@PathVariable Long id) {
    return roleService.getById(id);
  }

  /**
   * 查询所有角色
   *
   * @return 所有角色信息列表
   */
  @GetMapping("/list")
  public List<Role> list() {
    return roleService.list();
  }
}
