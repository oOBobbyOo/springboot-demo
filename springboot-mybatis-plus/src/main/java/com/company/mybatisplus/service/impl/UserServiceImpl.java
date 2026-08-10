package com.company.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.company.mybatisplus.common.ResultCode;
import com.company.mybatisplus.entity.User;
import com.company.mybatisplus.exception.BusinessException;
import com.company.mybatisplus.mapper.UserMapper;
import com.company.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户表 服务实现类
 *
 * @author Henry
 * @since 2026-08-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        // 校验用户名是否已存在
        long count = this.count(new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName()));

        if (count > 0) {
            throw new BusinessException(ResultCode.USERNAME_EXIST.getCode(), ResultCode.USERNAME_EXIST.getMessage());
        }

        // 保存用户
        boolean success = this.save(user);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "新增用户失败");
        }
    }

    /**
     * 修改用户
     *
     * @param user 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        // 校验ID是否存在
        if (user.getId() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "用户ID不能为空");
        }

        // 校验用户是否存在
        User oldUser = this.getById(user.getId());
        if (oldUser == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }

        // 如果修改了用户名，校验新用户名是否被其他用户使用
        if (!oldUser.getUserName().equals(user.getUserName())) {
            long count = this.count(
                    new LambdaQueryWrapper<User>()
                            .eq(User::getUserName, user.getUserName())
                            .ne(User::getId, user.getId()));

            if (count > 0) {
                throw new BusinessException(ResultCode.USERNAME_EXIST.getCode(), ResultCode.USERNAME_EXIST.getMessage());
            }
        }

        // 更新用户
        boolean success = this.updateById(user);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "修改用户失败");
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 校验用户是否存在
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }

        // 删除用户
        boolean success = this.removeById(id);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "删除用户失败");
        }
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户ID集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchUsers(List<Long> ids) {
        // 校验用户ID集合不能为空
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "用户ID集合不能为空");
        }

        // 批量删除用户
        boolean success = this.removeByIds(ids);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "批量删除用户失败");
        }
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public User getUserById(Long id) {
        User user = this.getById(id);

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMessage());
        }

        return user;
    }

    /**
     * 查询所有用户列表
     *
     * @return 用户列表
     */
    @Override
    public List<User> getUserList() {
        return this.list();
    }
}