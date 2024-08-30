package com.LeitnerLearn.backend.Config;

import com.LeitnerLearn.backend.Filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
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
      .csrf(CsrfConfigurer::disable)
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/api/v1/users/register").permitAll()
          .requestMatchers("/api/v1/users/**").hasRole("USER")
          .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults())  // HTTP 기본 인증 사용
      .formLogin(Customizer.withDefaults())  // 폼 로그인 사용
      .logout(Customizer.withDefaults())  // 로그아웃 사용
      .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
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