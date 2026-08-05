package com.company.mybatis.service.impl;

import com.company.mybatis.entity.Clazz;
import com.company.mybatis.entity.Student;
import com.company.mybatis.mapper.ClazzMapper;
import com.company.mybatis.mapper.StudentMapper;
import com.company.mybatis.service.StudentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Henry
 */
@Service
public class StudentServiceImpl implements StudentService {

  private final StudentMapper studentMapper;

  private final ClazzMapper clazzMapper;

  public StudentServiceImpl(StudentMapper studentMapper, ClazzMapper clazzMapper) {
    this.studentMapper = studentMapper;
    this.clazzMapper = clazzMapper;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Student createStudent(Student student) {
    // 参数校验
    validateStudent(student);

    studentMapper.insert(student);

    return student;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Student updateStudent(Integer id, Student student) {
    if (id == null) {
      throw new IllegalArgumentException("学生ID不能为空");
    }

    // 检查学生是否存在
    Student existingStudent = studentMapper.selectById(id);
    if (existingStudent == null) {
      throw new RuntimeException("学生不存在，ID: " + id);
    }

    // 参数校验
    validateStudent(student);

    // 设置ID
    student.setId(id);

    studentMapper.update(student);

    return studentMapper.selectById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteStudent(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("学生ID不能为空");
    }

    // 检查学生是否存在
    Student existingStudent = studentMapper.selectById(id);
    if (existingStudent == null) {
      throw new RuntimeException("学生不存在，ID: " + id);
    }

    studentMapper.delete(id);
  }

  @Override
  public List<Student> getAllStudents() {
    return studentMapper.selectAll();
  }

  @Override
  public Student getStudentById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("学生ID不能为空");
    }

    // 获取学生信息，包含所属班级
    return studentMapper.selectWithClassById(id);
  }

  @Override
  public List<Student> getStudentsByClassId(Integer classId) {
    if (classId == null) {
      throw new IllegalArgumentException("班级ID不能为空");
    }
    return studentMapper.selectByClassId(classId);
  }

  @Override
  public List<Student> searchByName(String name) {
    if (!StringUtils.hasText(name)) {
      throw new IllegalArgumentException("搜索名称不能为空");
    }
    return studentMapper.searchByName(name);
  }

  /**
   * 校验学生信息
   *
   * @param student 学生信息
   */
  private void validateStudent(Student student) {
    if (student == null) {
      throw new IllegalArgumentException("学生信息不能为空");
    }

    if (!StringUtils.hasText(student.getName())) {
      throw new IllegalArgumentException("学生姓名不能为空");
    }

    if (student.getName().length() > 50) {
      throw new IllegalArgumentException("学生姓名长度不能超过50个字符");
    }

    if (student.getAge() == null) {
      throw new IllegalArgumentException("学生年龄不能为空");
    }

    if (student.getAge() < 0 || student.getAge() > 100) {
      throw new IllegalArgumentException("学生年龄范围应在0-100之间");
    }

    if (student.getClassId() != null) {
      Clazz clazz = clazzMapper.selectById(student.getClassId());
      if (clazz == null) {
        throw new IllegalArgumentException("班级不存在，classId=" + student.getClassId());
      }
    }
  }
}
