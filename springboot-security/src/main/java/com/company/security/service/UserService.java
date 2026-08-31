package com.company.security.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.company.security.entity.User;
import com.company.security.vo.UserVO;

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
    UserVO getUserById(Long id);

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    UserVO getCurrentUserInfo();
}
