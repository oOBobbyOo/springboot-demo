package com.company.mybatis.entity;

import lombok.Data;

/**
 * @author Henry
 */
@Data
public class Student {

  private Integer id;

  private String name;

  private Integer age;

  private Integer classId;

  /** 学生所属班级 多对一关系：一个学生属于一个班级 */
  private Clazz clazz;
}
