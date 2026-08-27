package com.company.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT 工具类
 * <p>
 * 【架构考量】：<br/>
 * 1. 使用 JJWT 0.13.x API，线程安全，性能优异。<br/>
 * 2. 统一使用 HMAC-SHA256 算法进行签名，保证防篡改。<br/>
 * 3. 提供丰富的 Extract 方法，支持从 Token 中安全提取业务数据。
 *
 * @author Henry
 */
@Slf4j
@Component
public class JwtUtils {

   @Value("${jwt.secret}")
   private String secret;

   @Value("${jwt.expiration}")
   private Long expiration;

   @Value("${jwt.refresh-expiration}")
   private Long refreshExpiration;

   /**
    * 从 Base64 编码的字符串生成安全的 SecretKey
    * JJWT 0.12+ 推荐使用 Keys.hmacShaKeyFor，它会自动校验密钥长度是否符合算法要求，
    * 避免弱密钥导致的安全漏洞。
    */
   private SecretKey getSignInKey() {
      byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
      return Keys.hmacShaKeyFor(keyBytes);
   }

   // ==================== 生成 Token ====================

   /**
    * 生成 Access Token
    * @param userDetails Spring Security 的用户详情对象
    * @param extraClaims 额外需要存入 Token 的业务数据 (如 userId, deptId 等)
    * @return JWT 字符串
    */
   public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
      return buildToken(extraClaims, userDetails, expiration);
   }

   /**
    * 生成 Refresh Token (通常不需要携带额外业务数据，仅用于身份刷新)
    */
   public String generateRefreshToken(UserDetails userDetails) {
      return buildToken(new HashMap<>(), userDetails, refreshExpiration);
   }

   /**
    * 构建 Token 的核心方法
    */
   private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expireTime) {
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + expireTime);

      return Jwts.builder()
              .claims(extraClaims) // 1. 放入自定义业务 Claims
              .subject(userDetails.getUsername()) // 2. subject 固定存放 username (标准规范)
              .issuedAt(now) // 3. 签发时间
              .expiration(expiryDate) // 4. 过期时间 (JJWT 0.12+ 使用 .expiration() 替代 .setExpiration())
              .signWith(getSignInKey(), Jwts.SIG.HS256) // 5. 签名算法与密钥
              .compact();
   }

   // ==================== 解析与提取信息 ====================

   /**
    * 从 Token 中提取用户名 (subject)
    */
   public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
   }

   /**
    * 从 Token 中提取自定义业务数据 (例如: extractClaim(token, claims -> claims.get("userId", Long.class)))
    */
   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }

   /**
    * 解析 Token 获取所有 Claims
    * 此处不捕获异常，而是将异常抛给调用方 (JwtAuthenticationFilter)，
    * 由 Filter 统一区分是“过期”、“签名错误”还是“格式错误”，从而返回更精确的 401 错误信息。
    */
   private Claims extractAllClaims(String token) {
      return Jwts.parser()
              .verifyWith(getSignInKey()) // 设置校验密钥
              .build()
              .parseSignedClaims(token) // 解析并校验签名
              .getPayload();
   }

   // ==================== 校验 Token ====================

   /**
    * 校验 Token 是否有效
    * @param token 待校验的 Token
    * @param userDetails 当前请求对应的用户信息 (从数据库/Redis加载)
    * @return true: 有效, false: 无效
    */
   public boolean isTokenValid(String token, UserDetails userDetails) {
      try {
         final String username = extractUsername(token);
         // 1. 用户名必须匹配
         // 2. Token 不能过期
         return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
      } catch (ExpiredJwtException e) {
         log.warn("JWT Token 已过期: {}", e.getMessage());
         return false;
      } catch (SignatureException | MalformedJwtException e) {
         log.warn("JWT Token 签名无效或格式错误: {}", e.getMessage());
         return false;
      } catch (Exception e) {
         log.error("JWT Token 校验发生未知异常", e);
         return false;
      }
   }

   /**
    * 检查 Token 是否已过期
    */
   public boolean isTokenExpired(String token) {
      try {
         Date expirationDate = extractExpiration(token);
         return expirationDate.before(new Date());
      } catch (ExpiredJwtException e) {
         // 如果已经过期，extractExpiration 会抛出 ExpiredJwtException，这也意味着它确实过期了
         return true;
      }
   }

   private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

   /**
    * 获取 Token 剩余有效时间 (毫秒)
    * 用于前端判断是否需要静默刷新 Token，或后端判断是否需要提前续签。
    */
   public long getRemainingTime(String token) {
      try {
         Date expirationDate = extractExpiration(token);
         long remaining = expirationDate.getTime() - System.currentTimeMillis();
         return Math.max(0, remaining);
      } catch (Exception e) {
         return 0;
      }
   }

}
