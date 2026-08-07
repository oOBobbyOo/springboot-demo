package com.company.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author henry
 * @since 2026-08-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
