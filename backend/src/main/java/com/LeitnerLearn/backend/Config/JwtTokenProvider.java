package com.LeitnerLearn.backend.Config;

import com.LeitnerLearn.backend.Entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long validityInMilliseconds;

  public String createToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
      .setSubject(userDetails.getUsername())  // 토큰 제목
      .claim("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority())  // 토큰 내 정보
      .setIssuedAt(now)  // 토큰 발급일자
      .setExpiration(validity)  // 토큰 만기일자₩
      .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))  // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
      .compact();  // 토큰 생성
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUsername(String token) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
