package com.company.mybatis.mapper;

import com.company.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Henry
 */
@Mapper
public interface UserMapper {

  /**
   * 根据 Id 查询用户
   *
   * @param id 用户的唯一标识符
   * @return 用户响应对象
   */
  User selectById(Long id);

  /**
   * 查询所有用户
   *
   * @return 用户响应列表
   */
  List<User> selectAll();

  /**
   * 新增用户
   *
   * @param user 用户对象
   * @return 影响的行数
   */
  int insert(User user);

  /**
   * 根据 Id 更新用户
   *
   * @param user 用户对象（需包含待更新的 Id）
   * @return 影响的行数
   */
  int updateById(User user);

  /**
   * 根据 Id 删除用户
   *
   * @param id 用户的唯一标识符
   * @return 影响的行数
   */
  int deleteById(Long id);

  /**
   * 根据条件查询用户
   *
   * @param userName 用户名（用于模糊查询或精确匹配）
   * @return 符合条件的用户列表
   */
  List<User> selectByCondition(@Param("userName") String userName);
}
