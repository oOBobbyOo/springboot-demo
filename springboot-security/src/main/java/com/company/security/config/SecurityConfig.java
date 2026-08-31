package com.company.security.config;

import com.company.security.filter.JwtAuthenticationFilter;
import com.company.security.handler.CustomAccessDeniedHandler;
import com.company.security.handler.CustomAuthenticationEntryPoint;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 核心配置类
 *
 * <p>【架构考量】：<br>
 * 1. 采用前后端分离架构，使用 JWT 进行无状态认证。 <br>
 * 2. 启用 @EnableMethodSecurity 以支持方法级别的细粒度权限控制（如 @PreAuthorize("hasRole('ADMIN')"）。 <br>
 * 3. 全面使用 Spring Security 6.x 推荐的 Lambda DSL 语法，代码更简洁、类型安全。
 *
 * @author Henry
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;

  /**
   * 白名单路径配置 <br>
   * （建议提取到配置中心或 application.yml 中动态管理）
   */
  private static final String[] WHITE_LIST = {
    // 登录
    "/api/auth/login",
    // 注册
    "/api/auth/register",
    // 验证码
    "/api/auth/captcha",
    // 刷新 Token
    "/api/auth/refresh-token",
    // Swagger/OpenAPI 文档
    "/v3/api-docs/**",
    // Swagger UI
    "/swagger-ui/**",
    // 健康检查（K8s/探针必备）
    "/actuator/health",
    // 全局错误处理
    "/error"
  };

  /** 核心安全过滤器链配置 */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // 【1：禁用 CSRF】
        // 前后端分离且使用 JWT（存放在 Header 或 LocalStorage）时，不依赖 Cookie 传递凭证，
        // 天然免疫 CSRF（跨站请求伪造）攻击。禁用 CSRF 可避免不必要的 Token 校验开销。
        .csrf(AbstractHttpConfigurer::disable)

        // 【2：CORS 跨域配置】
        // 前后端分离必配。注意：生产环境严禁使用 allowedOrigins("*")，必须指定具体域名。
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // 【3：无状态 Session 管理】
        // 服务端不创建 HttpSession，完全依赖 JWT。
        // 优势：服务器内存零开销，完美支持微服务/集群的水平扩展。
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 【4：接口授权规则 (Authorization)】
        .authorizeHttpRequests(
            auth ->
                auth
                    // 1. 放行白名单接口
                    .requestMatchers(WHITE_LIST)
                    .permitAll()
                    // 2. 放行 OPTIONS 预检请求（CORS 跨域必备）
                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    // 3. 静态资源放行（如果有）
                    .requestMatchers(HttpMethod.GET, "/static/**", "/**/*.html", "/**/*.ico")
                    .permitAll()
                    // 4. 兜底规则：其他所有请求必须经过认证
                    .anyRequest()
                    .authenticated())

        // 【5：统一异常处理】
        // 前后端分离需要统一返回 JSON 格式的业务错误码，
        // 而不是 Spring Security 默认的 403 HTML 页面或 302 重定向到登录页。
        .exceptionHandling(
            exceptions ->
                exceptions
                    // 处理 401 未认证（如 Token 过期、未携带 Token）
                    .authenticationEntryPoint(authenticationEntryPoint)
                    // 处理 403 未授权（如 权限不足、角色不匹配）
                    .accessDeniedHandler(accessDeniedHandler))

        // 【6：安全响应头 (Headers)】
        // 开启基础的安全头防御，防止 XSS、点击劫持等常见 Web 攻击。
        .headers(
            headers ->
                headers
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny) // 禁止被 iframe 嵌套，防点击劫持
                    .contentTypeOptions(content -> {}) // 禁用浏览器 MIME 类型嗅探
                    .xssProtection(xss -> {}) // 开启 XSS 过滤
            )

    // 【7：注入自定义 JWT 过滤器】
    // 在 Spring Security 原生的 UsernamePasswordAuthenticationFilter 之前执行。
    // 负责拦截请求、解析 JWT、校验签名/有效期，并将用户信息塞入 SecurityContext。
     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * 密码编码器 <br>
   * 业界标准 BCrypt 算法。自带随机盐值，抗彩虹表攻击，且计算成本可调（防暴力破解）。 <br>
   * 严禁使用 MD5/SHA1 等不安全的哈希算法。
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 认证管理器 <br>
   * 暴露 AuthenticationManager，供 AuthServiceImpl.login() 调用 authenticate() 触发标准认证流程。 <br>
   * 由于上下文中存在唯一的 UserDetailsService + PasswordEncoder Bean，Spring Security 会自动装配 <br>
   * DaoAuthenticationProvider，统一完成密码校验（PasswordEncoder）与账号状态校验（UserDetails.isEnabled）。
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * CORS 跨域配置源 <br>
   * 生产环境必须严格限制允许的域名、方法和请求头。
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // 生产环境应替换为具体的前端域名，如 https://admin.yourcompany.com
    configuration.setAllowedOrigins(List.of("https://your-domain.com"));
    // 用于开发环境或特殊场景（支持通配符 "*"）
    configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "chrome-extension://*"));
    // 允许的 HTTP 方法
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // 允许的请求头
    configuration.setAllowedHeaders(List.of("*"));
    // 允许携带 Cookie（虽然 JWT 不用，但某些第三方组件可能需要）
    configuration.setAllowCredentials(true);
    // 预检请求缓存 1 小时，减少 OPTIONS 请求次数
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
