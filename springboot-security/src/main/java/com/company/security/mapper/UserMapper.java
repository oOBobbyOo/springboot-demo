package com.company.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Henry
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
