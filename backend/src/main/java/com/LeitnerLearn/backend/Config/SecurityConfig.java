package com.LeitnerLearn.backend.Config;

import com.LeitnerLearn.backend.Filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenFilter jwtTokenFilter;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(CsrfConfigurer::disable)  // CSRF 보호 비활성화
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/api/v1/auth/login", "/api/v1/users/register").permitAll()  // 로그인 및 회원가입은 인증 없이 접근 가능
          .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
          .requestMatchers("/api/v1/users/**").hasRole("USER")  // USER 역할을 가진 사용자만 접근 가능
          .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")  // ADMIN 역할을 가진 사용자만 접근 가능
          .anyRequest().authenticated()  // 그 외의 모든 요청은 인증 필요
      )
      .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}