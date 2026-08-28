package com.company.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Henry
 */
@Data
public class Base implements Serializable {

   /**
    * 主键 ID (雪花算法，在 yml 中已全局配置，此处注解可省略，但写上更直观)
    */
   @TableId(type = IdType.ASSIGN_ID)
   private Long id;

   /**
    * 创建时间 (自动填充)
    */
   @TableField(fill = FieldFill.INSERT)
   private LocalDateTime createTime;

   /**
    * 更新时间 (自动填充)
    */
   @TableField(fill = FieldFill.INSERT_UPDATE)
   private LocalDateTime updateTime;

   /**
    * 创建人 ID (自动填充)
    */
   @TableField(fill = FieldFill.INSERT)
   private Long createBy;

   /**
    * 修改人 ID (自动填充)
    */
   @TableField(fill = FieldFill.INSERT_UPDATE)
   private Long updateBy;

   /**
    * 逻辑删除标志 (0:未删除, 1:已删除)
    * 在 yml 中已全局配置，此处注解可省略。
    * 加上 @TableLogic 后，MP 的 select 会自动追加 where deleted=0，delete 会自动转为 update set deleted=1。
    */
   @TableLogic
   private Integer deleted;
}
