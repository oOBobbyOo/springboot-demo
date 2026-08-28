package com.company.security.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.company.security.entity.User;

/**
 * @author Henry
 */
public interface UserService extends IService<User> {

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);
}
