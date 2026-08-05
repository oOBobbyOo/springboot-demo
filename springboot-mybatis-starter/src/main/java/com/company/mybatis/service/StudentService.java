package com.company.mybatis.service;

import com.company.mybatis.entity.Student;
import java.util.List;

/**
 * @author Henry
 */
public interface StudentService {

  /**
   * 创建学生
   *
   * @param student 学生信息
   * @return 创建的学生信息
   */
  Student createStudent(Student student);

  /**
   * 更新学生
   *
   * @param id 学生id
   * @param student 学生信息
   * @return 更新后的学生信息
   */
  Student updateStudent(Integer id, Student student);

  /**
   * 删除学生
   *
   * @param id 学生id
   */
  void deleteStudent(Integer id);

  /**
   * 查询所有学生
   *
   * @return 学生列表
   */
  List<Student> getAllStudents();

  /**
   * 根据ID查询学生
   *
   * @param id 学生id
   * @return 学生信息
   */
  Student getStudentById(Integer id);

  /**
   * 根据班级ID查询学生
   *
   * @param classId 班级id
   * @return 学生列表
   */
  List<Student> getStudentsByClassId(Integer classId);

  /**
   * 搜索学生
   *
   * @param name 学生名字
   * @return 匹配搜索的学生列表
   */
  List<Student> searchByName(String name);
}
