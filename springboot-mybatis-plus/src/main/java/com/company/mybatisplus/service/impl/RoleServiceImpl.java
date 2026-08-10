package com.company.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.company.mybatisplus.common.ResultCode;
import com.company.mybatisplus.entity.Role;
import com.company.mybatisplus.entity.User;
import com.company.mybatisplus.exception.BusinessException;
import com.company.mybatisplus.mapper.RoleMapper;
import com.company.mybatisplus.service.RoleService;
import com.company.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author Henry
 * @since 2026-08-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final UserService userService;

    public RoleServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * 新增角色
     *
     * @param role 角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role) {
        // 校验角色编码是否已存在
        long count = this.count(new LambdaQueryWrapper<Role>().eq(Role::getRoleCode, role.getRoleCode()));

        if (count > 0) {
            throw new BusinessException(ResultCode.ROLE_CODE_EXIST.getCode(), ResultCode.ROLE_CODE_EXIST.getMessage());
        }

        // 校验角色名称是否已存在
        long roleNameCount = this.count(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, role.getRoleName()));

        if (roleNameCount > 0) {
            throw new BusinessException(ResultCode.ROLE_NAME_EXIST.getCode(), ResultCode.ROLE_NAME_EXIST.getMessage());
        }

        // 保存角色
        boolean success = this.save(role);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "新增角色失败");
        }
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        // 校验角色ID
        if (role.getId() == null) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "角色ID不能为空");
        }

        // 校验角色是否存在
        Role oldRole = this.getById(role.getId());
        if (oldRole == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST.getCode(), ResultCode.ROLE_NOT_EXIST.getMessage());
        }

        // 如果修改了角色编码，校验是否被其他角色使用
        if (!oldRole.getRoleCode().equals(role.getRoleCode())) {
            long count = this.count(
                    new LambdaQueryWrapper<Role>()
                            .eq(Role::getRoleCode, role.getRoleCode())
                            .ne(Role::getId, role.getId()));

            if (count > 0) {
                throw new BusinessException(ResultCode.ROLE_CODE_EXIST.getCode(), ResultCode.ROLE_CODE_EXIST.getMessage());
            }
        }

        // 如果修改了角色名称，校验是否被其他角色使用
        if (!oldRole.getRoleName().equals(role.getRoleName())) {
            long count = this.count(
                    new LambdaQueryWrapper<Role>()
                            .eq(Role::getRoleName, role.getRoleName())
                            .ne(Role::getId, role.getId()));

            if (count > 0) {
                throw new BusinessException(ResultCode.ROLE_NAME_EXIST.getCode(), ResultCode.ROLE_NAME_EXIST.getMessage());
            }
        }

        // 更新角色
        boolean success = this.updateById(role);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "修改角色失败");
        }
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 校验角色是否存在
        Role role = this.getById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST.getCode(), ResultCode.ROLE_NOT_EXIST.getMessage());
        }

        // 校验该角色下是否还有用户
        long userCount = userService.count(new LambdaQueryWrapper<User>().eq(User::getRoleId, id));

        if (userCount > 0) {
            throw new BusinessException(ResultCode.ROLE_HAS_USERS.getCode(), ResultCode.ROLE_HAS_USERS.getMessage());
        }

        // 删除角色
        boolean success = this.removeById(id);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "删除角色失败");
        }
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ID集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchRoles(List<Long> ids) {
        // 校验角色ID集合不能为空
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "角色ID集合不能为空");
        }

        // 批量删除角色
        boolean success = this.removeByIds(ids);

        if (!success) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR.getCode(), "批量删除角色失败");
        }
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @Override
    public Role getRoleById(Long id) {
        Role role = this.getById(id);

        if (role == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST.getCode(), ResultCode.ROLE_NOT_EXIST.getMessage());
        }

        return role;
    }

    /**
     * 查询所有角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<Role> getRoleList() {
        return this.list();
    }
}