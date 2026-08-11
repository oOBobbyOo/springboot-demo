package com.company.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置类
 *
 * @author Henry
 */
@Configuration
@MapperScan("com.company.mybatisplus.mapper")
public class MybatisPlusConfig {

  /**
   * 注册 MyBatis-Plus 分页插件
   *
   * @return MybatisPlusInterceptor
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

    // 分页插件
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    paginationInnerInterceptor.setMaxLimit(500L);
    // 溢出总页数后是否进行处理，默认 false，当当前页大于总页数时，自动回到第一页
    paginationInnerInterceptor.setOverflow(false);
    // 单页分页结果统计优化
    paginationInnerInterceptor.setOptimizeJoin(true);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);

    // 乐观锁插件
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

    // 防止全表更新与删除插件
    interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

    // 性能分析插件（生产环境建议关闭）
    // interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());

    return interceptor;
  }
}
