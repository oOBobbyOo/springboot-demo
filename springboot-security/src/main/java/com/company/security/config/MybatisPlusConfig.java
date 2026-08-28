package com.company.security.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 核心配置
 *
 * @author Henry
 */
@Configuration
@MapperScan("com.company.security.mapper")
public class MybatisPlusConfig {

   @Bean
   public MybatisPlusInterceptor mybatisPlusInterceptor() {
      MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
      // 添加分页插件
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
      return interceptor;
   }

   /**
    * 自动填充处理器 (创建时间、更新时间)
    */
   @Component
   public static class MyMetaObjectHandler implements MetaObjectHandler {

      @Override
      public void insertFill(MetaObject metaObject) {
         this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
         this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
      }

      @Override
      public void updateFill(MetaObject metaObject) {
         this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
      }
   }
}
