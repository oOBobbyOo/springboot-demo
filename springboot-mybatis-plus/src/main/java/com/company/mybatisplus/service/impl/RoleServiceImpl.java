package com.company.mybatisplus.service.impl;

import com.company.mybatisplus.entity.Role;
import com.company.mybatisplus.mapper.RoleMapper;
import com.company.mybatisplus.service.RoleService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Henry
 * @since 2026-08-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
