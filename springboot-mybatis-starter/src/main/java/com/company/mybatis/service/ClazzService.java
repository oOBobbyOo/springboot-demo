package com.company.mybatis.service;

import com.company.mybatis.entity.Clazz;
import java.util.List;

/**
 * @author Henry
 */
public interface ClazzService {

  /**
   * 创建班级
   *
   * @param clazz 班级信息
   * @return 班级信息
   */
  Clazz createClass(Clazz clazz);

  /**
   * 更新班级
   *
   * @param id 班级id
   * @param clazz 班级信息
   * @return 更新后的班级信息
   */
  Clazz updateClass(Integer id, Clazz clazz);

  /**
   * 删除班级
   *
   * @param id 班级id
   */
  void deleteClass(Integer id);

  /**
   * 根据 ID 查询班级
   *
   * @param id 班级id
   * @return 班级信息
   */
  Clazz getClassById(Integer id);

  /**
   * 查询所有班级
   *
   * @return 班级信息列表
   */
  List<Clazz> getAllClasses();
}
