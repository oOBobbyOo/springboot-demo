package com.company.mybatis.mapper;

import com.company.mybatis.entity.Clazz;
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * @author Henry
 */
@Mapper
public interface ClazzMapper {

  /**
   * 插入班级
   *
   * @param clazz 班级信息
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Insert(
      """
            INSERT INTO `class`(name)
            VALUES (#{name})
            """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Clazz clazz);

  /**
   * 更新班级
   *
   * @param clazz 班级信息
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Update(
      """
            UPDATE `class`
            SET name = #{name}
            WHERE id = #{id}
            """)
  int update(Clazz clazz);

  /**
   * 删除班级
   *
   * @param id 班级id
   * @return 影响的行数，成功返回1，失败返回0
   */
  @Delete(
      """
            DELETE FROM `class`
            WHERE id = #{id}
            """)
  int delete(Integer id);

  /**
   * 根据ID查询班级
   *
   * @param id 班级id
   * @return 班级信息，如果不存在则返回null
   */
  @Select(
      """
            SELECT id,
                   name
            FROM `class`
            WHERE id = #{id}
            """)
  Clazz selectById(Integer id);

  /**
   * 根据ID查询班级（包含学生列表）
   *
   * @param id 班级id
   * @return 班级信息，如果不存在则返回null
   */
  @Select(
      """
            SELECT id,
                   name
            FROM `class`
            WHERE id = #{id}
            """)
  @Results({
    @Result(column = "id", property = "id", id = true),
    @Result(column = "name", property = "name"),
    @Result(
        column = "id",
        property = "students",
        many =
            @Many(
                select = "com.company.mybatis.mapper.StudentMapper.selectByClassId",
                fetchType = FetchType.EAGER))
  })
  Clazz selectWithStudentsById(Integer id);

  /**
   * 查询所有班级
   *
   * @return 所有班级列表，如果没有班级则返回空列表
   */
  @Select(
      """
            SELECT id,
                   name
            FROM `class`
            """)
  List<Clazz> selectAll();
}
