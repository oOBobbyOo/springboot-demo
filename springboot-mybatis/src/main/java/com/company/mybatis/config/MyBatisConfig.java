package com.company.mybatis.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author Henry
 */
@Configuration
public class MyBatisConfig {

  /**
   * 配置 SqlSessionFactory
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory(
      DataSource dataSource,
      @Value("${mybatis.config-location:classpath:mybatis-config.xml}") Resource configLocation,
      @Value("${mybatis.mapper-locations:classpath:mapper/*.xml}") String mapperLocations)
      throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);

    // 设置 MyBatis 主配置文件（可选）
    if (configLocation.exists()) {
      factoryBean.setConfigLocation(configLocation);
    }

    // 设置 Mapper XML 文件位置
    factoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(mapperLocations));

    // 设置类型别名包
    factoryBean.setTypeAliasesPackage("com.company.mybaits.entity");

    return factoryBean.getObject();
  }

  /**
   * 配置 Mapper 扫描器
   * 注意：需要使用 static 方法，防止与其他 Bean 的依赖冲突
   */
  @Bean
  public static MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage("com.company.mybatis.mapper");
    scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    return scannerConfigurer;
  }
}
