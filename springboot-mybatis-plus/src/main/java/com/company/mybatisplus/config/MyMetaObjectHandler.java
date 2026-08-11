package com.company.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * MyBatis-Plus 自动填充处理器
 *
 * @author Henry
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  /**
   * 插入时自动填充
   *
   * <p>执行 insert、save、saveBatch 等新增方法时会进入该方法。
   *
   * @param metaObject 元对象，可以获取当前实体类字段信息
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("开始插入填充...");
    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
  }

  /**
   * 更新时自动填充
   *
   * <p>执行 updateById、update、updateBatchById 等更新方法时会进入该方法。
   *
   * @param metaObject 元对象，可以获取当前实体类字段信息
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("开始更新填充...");
    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
  }
}
