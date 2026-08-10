package com.company.mybatisplus.controller;

import com.company.mybatisplus.common.PageResult;
import com.company.mybatisplus.common.Result;
import com.company.mybatisplus.dto.UserQuery;
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
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody User user) {
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID，路径参数
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户ID集合，请求体参数
     * @return 统一响应结果
     */
    @DeleteMapping("/deleteBatch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        userService.deleteBatchUsers(ids);
        return Result.success("批量删除成功");
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息，请求体参数，必须包含用户ID
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success("修改用户成功");
    }

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

    /**
     * 查询所有用户列表
     *
     * @return 统一响应结果
     */
    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> users = userService.getUserList();
        return Result.success(users);
    }

    /**
     * 分页条件查询用户
     *
     * @param query 查询条件
     * @return 统一分页结果
     */
    @GetMapping("/page")
    public Result<PageResult<User>> page(UserQuery query) {
        PageResult<User> pageResult = userService.getUserPage(query);
        return Result.success(pageResult);
    }
}