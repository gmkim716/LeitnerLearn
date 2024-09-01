package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Entity.User;
import com.LeitnerLearn.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    return new CustomUserDetails(user);  // User 엔티티를 CustomUserDetails로 변환`
  }
}
