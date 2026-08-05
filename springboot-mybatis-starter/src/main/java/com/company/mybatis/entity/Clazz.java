package com.company.mybatis.entity;

import java.util.List;
import lombok.Data;

/**
 * @author Henry
 */
@Data
public class Clazz {

  private Integer id;

  private String name;

  /** 班级下的学生 一对多关系：一个班级有多个学生 */
  private List<Student> students;
}
