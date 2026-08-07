package com.company.mybatisplus.service.impl;

import com.company.mybatisplus.entity.User;
import com.company.mybatisplus.mapper.UserMapper;
import com.company.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author henry
 * @since 2026-08-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
