package com.company.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 公共实体类
 *
 * @author Henry
 */
@Data
public class Base implements Serializable {

  /**
   * 创建时间
   *
   * <p>FieldFill.INSERT： <br>
   * 只在新增时自动填充
   */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 更新时间
   *
   * <p>FieldFill.INSERT_UPDATE： <br>
   * 新增和更新时都会自动填充
   */
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
