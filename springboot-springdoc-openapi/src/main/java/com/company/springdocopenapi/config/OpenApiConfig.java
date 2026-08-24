package com.company.springdocopenapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Henry
 */
@Configuration
public class OpenApiConfig {

   @Bean
   public OpenAPI customOpenApi(@Value("${springdoc.api-docs.version}") String appVersion) {
      return new OpenAPI()
              .components(new Components())
              .info(
                      new Info()
                              .title("API 接口文档")
                              .description("系统核心业务接口文档")
                              .version(appVersion)
                              .contact(new Contact()
                                      .name("开发团队")
                                      .email("dev@example.com")
                                      .url("http://springdoc.org"))
                              .license(new License()
                                      .name("Apache 2.0")
                                      .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
   }
}
