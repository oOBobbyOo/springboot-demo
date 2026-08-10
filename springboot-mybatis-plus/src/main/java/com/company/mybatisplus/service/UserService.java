package com.company.mybatisplus.service;

import com.company.mybatisplus.entity.User;
import com.baomidou.mybatisplus.spring.service.IService;

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
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);
}
