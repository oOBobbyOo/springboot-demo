package com.company.mybatisplus.service;

import com.company.mybatisplus.common.PageResult;
import com.company.mybatisplus.dto.UserQuery;
import com.company.mybatisplus.entity.User;
import com.baomidou.mybatisplus.spring.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Henry
 * @since 2026-08-07
 */
public interface UserService extends IService<User> {

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    void addUser(User user);

    /**
     * 修改用户
     *
     * @param user 用户信息
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID集合
     */
    void deleteBatchUsers(List<Long> ids);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);

    /**
     * 查询所有用户列表
     *
     * @return 用户列表
     */
    List<User> getUserList();

    /**
     * 分页条件查询用户
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<User> getUserPage(UserQuery query);
}
