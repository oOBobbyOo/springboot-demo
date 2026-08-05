package com.company.mybatis.controller;

import com.company.mybatis.entity.Student;
import com.company.mybatis.service.StudentService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * 创建学生
   *
   * @param student 学生信息
   * @return 创建的学生信息
   */
  @PostMapping
  public Student create(@RequestBody Student student) {
    return studentService.createStudent(student);
  }

  /**
   * 更新学生
   *
   * @param student 学生信息
   * @return 更新后的学生信息
   */
  @PutMapping("/{id}")
  public Student update(@PathVariable Integer id, @RequestBody Student student) {
    return studentService.updateStudent(id, student);
  }

  /**
   * 删除学生
   *
   * @param id 学生id
   * @return 操作信息
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Integer id) {
    studentService.deleteStudent(id);
    return "删除成功";
  }

  /**
   * 获取所有学生
   *
   * @return 学生列表
   */
  @GetMapping
  public List<Student> listAll() {
    return studentService.getAllStudents();
  }

  /**
   * 根据ID获取学生
   *
   * @param id 学生id
   * @return 学生信息
   */
  @GetMapping("/{id}")
  public Student getById(@PathVariable("id") Integer id) {
    return studentService.getStudentById(id);
  }

  /**
   * 根据班级ID获取学生
   *
   * @param classId 班级id
   * @return 学生列表
   */
  @GetMapping("/classes/{classId}")
  public List<Student> getByClassId(@PathVariable Integer classId) {
    return studentService.getStudentsByClassId(classId);
  }

  /**
   * 搜索学生
   *
   * @param name 学生名字
   * @return 匹配搜索的学生列表
   */
  @GetMapping("/search")
  public List<Student> search(@RequestParam String name) {
    return studentService.searchByName(name);
  }
}
