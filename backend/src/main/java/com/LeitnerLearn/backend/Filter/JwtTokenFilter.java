package com.LeitnerLearn.backend.Filter;

import com.LeitnerLearn.backend.Utils.JwtUtils;
import com.LeitnerLearn.backend.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * JWT 토큰을 헤더에서 추출해 인증을 처리하는 필터
 * 요청이 서버에 도달하기 전에 JWT를 검사하고, 유요한 경우 인증 정보를 설정한다
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      String token = resolveToken(request);

      if (token != null && jwtUtils.validateToken(token)) {
        String username = jwtUtils.getUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails != null) {
          UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT 토큰이 유효하지 않거나 만료되었습니다");
      return;
    }

    filterChain.doFilter(request, response);
  }
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);  // 7: "Bearer "의 길이
    }
    return null;
  }
}
