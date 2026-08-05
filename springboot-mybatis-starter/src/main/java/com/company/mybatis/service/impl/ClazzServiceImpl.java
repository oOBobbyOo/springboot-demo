package com.company.mybatis.service.impl;

import com.company.mybatis.entity.Clazz;
import com.company.mybatis.mapper.ClazzMapper;
import com.company.mybatis.service.ClazzService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Henry
 */
@Service
public class ClazzServiceImpl implements ClazzService {
  private final ClazzMapper clazzMapper;

  public ClazzServiceImpl(ClazzMapper clazzMapper) {
    this.clazzMapper = clazzMapper;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Clazz createClass(Clazz clazz) {
    // 参数校验
    validateClass(clazz);

    clazzMapper.insert(clazz);

    return clazz;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Clazz updateClass(Integer id, Clazz clazz) {
    if (id == null) {
      throw new IllegalArgumentException("班级ID不能为空");
    }

    // 检查班级是否存在
    Clazz existingClazz = clazzMapper.selectById(id);
    if (existingClazz == null) {
      throw new RuntimeException("班级不存在，ID: " + id);
    }

    // 参数校验
    validateClass(clazz);

    // 设置ID
    clazz.setId(id);

    clazzMapper.update(clazz);

    return clazz;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteClass(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("班级ID不能为空");
    }

    // 检查班级是否存在
    Clazz existingClazz = clazzMapper.selectById(id);
    if (existingClazz == null) {
      throw new RuntimeException("班级不存在，ID: " + id);
    }

    clazzMapper.delete(id);
  }

  @Override
  public Clazz getClassById(Integer id) {
    return clazzMapper.selectById(id);
  }

  @Override
  public List<Clazz> getAllClasses() {
    return clazzMapper.selectAll();
  }

  /**
   * 校验班级信息
   *
   * @param clazz 班级信息
   */
  private void validateClass(Clazz clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("班级信息不能为空");
    }

    if (!StringUtils.hasText(clazz.getName())) {
      throw new IllegalArgumentException("班级名称不能为空");
    }
  }
}
