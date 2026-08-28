package com.company.security.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.company.security.entity.User;
import com.company.security.mapper.UserMapper;
import com.company.security.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Henry
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }
}
