package com.company.mybatisplus.controller;

import com.company.mybatisplus.common.PageResult;
import com.company.mybatisplus.common.Result;
import com.company.mybatisplus.dto.RoleQuery;
import com.company.mybatisplus.entity.Role;
import com.company.mybatisplus.service.RoleService;

import java.util.List;

import jakarta.validation.Valid;
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
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public Result<Void> add(@Valid @RequestBody Role role) {
        roleService.addRole(role);
        return Result.success("新增角色成功");
    }

    /**
     * 根据角色ID删除角色
     *
     * @param id 角色ID
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.success("删除角色成功");
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ID集合，请求体参数
     * @return 统一响应结果
     */
    @DeleteMapping("/deleteBatch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        roleService.deleteBatchRoles(ids);
        return Result.success("批量删除成功");
    }

    /**
     * 修改角色信息
     *
     * @param role 角色信息，必须包含角色ID
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Role role) {
        roleService.updateRole(role);
        return Result.success("修改角色成功");
    }

    /**
     * 根据角色ID查询角色详情
     *
     * @param id 角色ID
     * @return 统一响应结果
     */
    @GetMapping("/get/{id}")
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return Result.success(role);
    }

    /**
     * 查询所有角色列表
     *
     * @return 统一响应结果
     */
    @GetMapping("/list")
    public Result<List<Role>> list() {
        List<Role> roles = roleService.getRoleList();
        return Result.success(roles);
    }

    /**
     * 分页条件查询角色
     *
     * @param query 查询条件
     * @return 统一分页结果
     */
    @GetMapping("/page")
    public Result<PageResult<Role>> page(RoleQuery query) {
        PageResult<Role> pageResult = roleService.getRolePage(query);
        return Result.success(pageResult);
    }
}