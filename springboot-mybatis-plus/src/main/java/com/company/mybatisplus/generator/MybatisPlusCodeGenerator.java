package com.company.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;

/**
 * MyBatis-Plus 代码生成器
 *
 * @author Henry
 */
public class MybatisPlusCodeGenerator {

  /** 项目根路径 */
  private static final String PROJECT_PATH = System.getProperty("user.dir");

  /** Java 代码输出目录 */
  private static final String JAVA_PATH = PROJECT_PATH + "/src/main/java";

  /** Mapper XML 输出目录 */
  private static final String XML_PATH = PROJECT_PATH + "/src/main/resources/mapper";

  /** 数据库连接信息，建议通过环境变量传入，避免密码提交到 Git */
  private static final String DB_URL =
      System.getenv()
          .getOrDefault(
              "DB_URL",
              "jdbc:mysql://127.0.0.1:3306/mybatis_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");

  /** 数据库用户名 */
  private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "root");

  /** 数据库密码 */
  private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "123456");

  static void main(String[] args) {

    // 要生成的表，多个表用逗号分隔，也可以直接通过 main 方法参数传入
    final String[] tables = args.length == 0 ? new String[] {"users", "roles"} : args;

    FastAutoGenerator.create(DB_URL, DB_USER, DB_PASSWORD)
        // 全局配置
        .globalConfig(
            builder ->
                builder
                    // 作者
                    .author("henry")
                    // 输出目录
                    .outputDir(JAVA_PATH)
                    // 注释日期格式
                    .commentDate("yyyy-MM-dd")
                    // 开启 swagger 模式
                    // .enableSwagger()
                    // 开启 springDoc 模式
                    // .enableSpringdoc()
                    // 生成后不打开输出目录
                    .disableOpenDir())

        // 包配置
        .packageConfig(
            builder ->
                builder
                    // 父包名
                    .parent("com.company.mybatisplus")
                    // 模块名 默认值: 无
                    // .moduleName("")
                    .moduleName("system")
                    // Entity 包名
                    .entity("entity")
                    // Mapper 包名
                    .mapper("mapper")
                    // Service 包名
                    .service("service")
                    // ServiceImpl 包名
                    .serviceImpl("service.impl")
                    // Controller 包名
                    .controller("controller")
                    // Mapper XML 输出目录
                    .pathInfo(Collections.singletonMap(OutputFile.xml, XML_PATH)))

        // 策略配置
        .strategyConfig(
            builder -> {

              // 设置需要生成的表
               builder.addInclude(tables);

              // 如果希望去掉表前缀，例如 sys_user 生成 User，可以打开下面配置
              builder.addTablePrefix("sys_");

              // Entity 策略
              builder
                  .entityBuilder()
                  // 启用 Lombok
                  .enableLombok()
                  // 生成字段注解 @TableField
                  .enableTableFieldAnnotation()
                  // 表名下划线转驼峰
                  .naming(NamingStrategy.underline_to_camel)
                  // 字段名下划线转驼峰
                  .columnNaming(NamingStrategy.underline_to_camel)
                  // 主键策略，如果数据库是自增主键，使用 AUTO
                  // 如果使用雪花算法，可以用 ASSIGN_ID
                  .idType(IdType.AUTO);

              // Controller 策略
              builder
                  .controllerBuilder()
                  // 生成 @RestController
                  .enableRestStyle();

              // Service 策略
              builder
                  .serviceBuilder()
                  .formatServiceFileName("%sService")
                  .formatServiceImplFileName("%sServiceImpl");

              // Mapper 策略
              builder
                  .mapperBuilder()
                  // 生成 @Mapper 注解
                  .enableMapperAnnotation()
                  // 生成 BaseResultMap
                  .enableBaseResultMap()
                  // 生成 Base_Column_List
                  .enableBaseColumnList();
            })

        // 模板引擎，默认是 Velocity，或使用 Freemarker
        // .templateEngine(new VelocityTemplateEngine())
        .templateEngine(new FreemarkerTemplateEngine())

        // 执行生成
        .execute();
  }
}
