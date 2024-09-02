package com.LeitnerLearn.backend.Utils;

import com.LeitnerLearn.backend.Service.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

  private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long expirationTime;

  // SecretKey를 HS256에 적합한 형태로 생성
  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes();
    if (keyBytes.length < 32) {  // 키 길이가 256비트(32바이트) 이상인지 확인
      throw new IllegalArgumentException("키가 너무 짧습니다. 256비트 이상의 키가 필요합니다");
    }
    return Keys.hmacShaKeyFor(keyBytes);  // SecretKey 생성
    }

  public String createToken(Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    Date now = new Date();
    Date validity = new Date(now.getTime() + expirationTime);

    // 토큰에 담길 정보 설정
    return Jwts.builder()
      .claim("userId", userDetails.getId())
      .claim("username", userDetails.getUsername())
      .claim("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority())  // 클레임 추가
      .setIssuedAt(now)  // 토큰 발급일자
      .setExpiration(validity)  // 토큰 만기일자
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // secretKey로 서명
      .compact();  // 토큰 생성
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      logger.error("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      logger.warn("만료된 토큰입니다");
    } catch (UnsupportedJwtException e) {
      logger.error("지원되지 않는 JWT 토큰입니다");
    } catch (IllegalArgumentException e) {
      logger.error("JWT 토큰이 비어있거나 잘못되었습니다.");
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
