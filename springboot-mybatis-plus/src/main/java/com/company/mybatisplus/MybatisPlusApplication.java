package com.company.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Henry
 */
@SpringBootApplication
@MapperScan("com.company.mybatisplus.**.mapper")
public class MybatisPlusApplication {

     static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

}
