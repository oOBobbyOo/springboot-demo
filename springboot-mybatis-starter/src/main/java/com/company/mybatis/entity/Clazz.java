package com.company.mybatis.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Henry
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clazz {

  private Integer id;

  private String name;

  /**
   * 班级下的学生
   *
   * <p>一对多关系：一个班级有多个学生
   */
  private List<Student> students;
}
