package com.company.mybatis.mapper;

import com.company.mybatis.entity.Student;
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * @author Henry
 */
@Mapper
public interface StudentMapper {

  /**
   * 插入学生
   *
   * @param student 学生信息
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Insert(
      """
            INSERT INTO students(name, age, class_id)
            VALUES (#{name}, #{age}, #{classId})
            """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Student student);

  /**
   * 更新学生
   *
   * @param student 学生信息
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Update(
      """
            UPDATE students
            SET name = #{name},
                age = #{age},
                class_id = #{classId}
            WHERE id = #{id}
            """)
  int update(Student student);

  /**
   * 删除学生
   *
   * @param id 学生id
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Delete(
      """
            DELETE FROM students
            WHERE id = #{id}
            """)
  int delete(Integer id);

  /**
   * 根据ID查询学生
   *
   * @param id 学生id
   * @return 学生信息
   */
  @Select(
      """
            SELECT id,
                   name,
                   age,
                   class_id AS classId
            FROM students
            WHERE id = #{id}
            """)
  Student selectById(Integer id);

  /**
   * 根据 ID 查询学生，并查询学生所属班级
   *
   * @param id 学生id
   * @return 学生信息
   */
  @Select(
      """
            SELECT id,
                   name,
                   age,
                   class_id
            FROM students
            WHERE id = #{id}
            """)
  @Results({
    @Result(column = "id", property = "id", id = true),
    @Result(column = "name", property = "name"),
    @Result(column = "age", property = "age"),
    @Result(column = "class_id", property = "classId"),
    @Result(
        column = "class_id",
        property = "clazz",
        one =
            @One(
                select = "com.company.mybatis.mapper.ClazzMapper.selectById",
                fetchType = FetchType.EAGER))
  })
  Student selectWithClassById(Integer id);

  /**
   * 查询所有学生
   *
   * @return 所有学生列表，如果没有学生则返回空列表
   */
  @Select(
      """
            SELECT id,
                   name,
                   age,
                   class_id AS classId
            FROM students
            """)
  List<Student> selectAll();

  /**
   * 根据班级ID查询学生列表
   *
   * @param classId 班级id
   * @return 该班级下的学生列表，如果没有学生则返回空列表
   */
  @Select(
      """
            SELECT id,
                   name,
                   age,
                   class_id AS classId
            FROM students
            WHERE class_id = #{classId}
            """)
  List<Student> selectByClassId(Integer classId);

  /**
   * 搜索学生
   *
   * @param name 学生名字
   * @return 匹配搜索的学生列表
   */
  @Select(
      """
          SELECT * FROM students WHERE name LIKE CONCAT('%', #{name}, '%')
      """)
  List<Student> searchByName(String name);
}
