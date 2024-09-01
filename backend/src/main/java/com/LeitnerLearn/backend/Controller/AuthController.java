package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Service.CustomUserDetailsService;
import com.LeitnerLearn.backend.Utils.JwtUtils;
import com.LeitnerLearn.backend.Dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtTokenProvider;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequestDto loginRequestDto) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
      return jwtTokenProvider.createToken(authentication);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("일치하지 않는 사용자 정보입니다.");
    }
  }
}
