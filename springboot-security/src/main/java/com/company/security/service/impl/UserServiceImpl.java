package com.company.security.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.company.security.entity.User;
import com.company.security.exception.BusinessException;
import com.company.security.mapper.UserMapper;
import com.company.security.service.UserService;
import com.company.security.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * 获取当前登录用户信息
     * JwtAuthenticationFilter 已将 userId 存入 SecurityContext 的 principal 中，此处直接读取。
     *
     * @return 当前登录用户信息
     */
    @Override
    public UserVO getCurrentUserInfo() {
        Long userId = getCurrentUserId();

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        return toUserVO(user);
    }

    /**
     * 从 SecurityContext 中获取当前登录用户ID
     *
     * @return 用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Long userId)) {
            throw new BusinessException(401, "未登录或登录已过期");
        }
        return userId;
    }

    /**
     * 实体转换为 VO，隐藏密码、状态等敏感字段
     *
     * @param user 用户实体
     * @return 用户 VO
     */
    private UserVO toUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setAvatar(user.getAvatar());
        vo.setGender(user.getGender());
        vo.setRemark(user.getRemark());
        vo.setCreateTime(user.getCreateTime());
        return vo;
    }
}
