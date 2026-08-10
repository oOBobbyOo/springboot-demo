package com.company.mybatisplus.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.company.mybatisplus.entity.Role;

import java.util.List;

/**
 * 角色表 服务类
 *
 * @author Henry
 * @since 2026-08-07
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增角色
     *
     * @param role 角色信息
     */
    void addRole(Role role);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteRole(Long id);

    /**
     * 批量删除角色
     *
     * @param ids 批量删除对应的角色ID
     */
    void deleteBatchRoles(List<Long> ids);

    /**
     * 修改角色
     *
     * @param role 角色信息
     */
    void updateRole(Role role);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    Role getRoleById(Long id);

    /**
     * 查询所有角色列表
     *
     * @return 角色列表
     */
    List<Role> getRoleList();
}
