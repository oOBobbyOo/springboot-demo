package com.company.security.filter;

import com.company.security.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Henry
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = getJwtFromRequest(request);

    if (StringUtils.hasText(jwt)) {
      try {
        // 尝试解析 (如果失败，会直接抛出具体异常，跳到下方的 catch)
        Long userId = jwtUtils.extractClaim(jwt, claims -> claims.get("userId", Long.class));
        String username = jwtUtils.extractUsername(jwt);

        // 校验通过，构建 Authentication 对象
        // 注意：这里没有查数据库！体现了 JWT 的无状态高性能优势。
        // 如果需要角色权限，可以在这里从 Redis 缓存中读取，而不是查 DB。
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

        // 将 username 存入 details，方便后续日志记录
        authentication.setDetails(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);

      } catch (ExpiredJwtException ex) {
        // 【精准拦截】：Token 过期
        request.setAttribute("jwt_error", "TOKEN_EXPIRED");
        log.debug("JWT 过期: {}", ex.getMessage());
      } catch (SignatureException ex) {
        // 【精准拦截】：签名被篡改或密钥不匹配
        request.setAttribute("jwt_error", "TOKEN_INVALID");
        log.warn("JWT 签名无效: {}", ex.getMessage());
      } catch (MalformedJwtException ex) {
        // 【精准拦截】：Token 格式错误 (如少了个句号)
        request.setAttribute("jwt_error", "TOKEN_MALFORMED");
        log.warn("JWT 格式错误: {}", ex.getMessage());
      } catch (Exception ex) {
        // 【兜底拦截】：其他未知解析错误
        request.setAttribute("jwt_error", "TOKEN_ERROR");
        log.error("JWT 解析未知异常", ex);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
