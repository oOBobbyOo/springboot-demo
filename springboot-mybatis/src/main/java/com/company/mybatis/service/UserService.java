package com.company.mybatis.service;

import com.company.mybatis.dto.UserCreateDto;
import com.company.mybatis.dto.UserUpdateDto;
import com.company.mybatis.entity.User;
import com.company.mybatis.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author Henry
 */
@Service
public class UserService {
  private final UserMapper userMapper;

  public UserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public User getUserById(Long id) {
    return userMapper.selectById(id);
  }

  public List<User> getAllUsers() {
    return userMapper.selectAll();
  }

  public User createUser(UserCreateDto userCreateDto) {
    User user = new User();
    user.setUserName(userCreateDto.getUserName());

    LocalDateTime now = LocalDateTime.now();
    user.setCreateTime(now);
    user.setUpdateTime(now);

    userMapper.insert(user);
    return user;
  }

  public User updateUser(Long id, UserUpdateDto userUpdateDto) {
    User user = userMapper.selectById(id);

    if (user == null) {
      throw new RuntimeException("用户不存在");
    }

    user.setUserName(userUpdateDto.getUserName());
    user.setUpdateTime(LocalDateTime.now());

    userMapper.updateById(user);

    return user;
  }

  public int deleteUser(Long id) {
    return userMapper.deleteById(id);
  }
}
