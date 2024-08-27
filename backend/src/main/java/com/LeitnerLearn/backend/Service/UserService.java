package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Entity.Role;
import com.LeitnerLearn.backend.Entity.User;
import com.LeitnerLearn.backend.Exception.UserNotFoundException;
import com.LeitnerLearn.backend.Repository.UserRepository;
import com.LeitnerLearn.backend.Dto.UserRequestDto;
import com.LeitnerLearn.backend.Dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id: " + id + " 사용자를 찾지 못했습니다"));
  }

  public ResponseEntity<UserResponseDto> registerUser(UserRequestDto userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .build();  // 메시지를 생략하고 상태 코드만 반환
    }

    // 유효성 검사
    if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 메시지를 생략하고 상태 코드만 반환
    }
    if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 메시지를 생략하고 상태 코드만 반환
    }

    // User 엔티티 생성 및 저장
    User user = User.builder()
      .username(userDto.getUsername())
      .email(userDto.getEmail())
      .password(userDto.getPassword())  // 실제 애플리케이션에서는 비밀번호를 암호화하는 것이 중요합니다.
      .role(Role.USER)
      .createdAt(LocalDateTime.now())
      .updatedAt(LocalDateTime.now())
      .build();

    User savedUser = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(savedUser));
  }

  public UserResponseDto updateUser(Long id, UserRequestDto user) {
    User foundUser = userRepository.findById(id)
      .orElseThrow(() -> new UserNotFoundException("id: " + id + " 사용자를 찾지 못했습니다"));

    // 기존 데이터를 기반으로 선택적 필드 업데이트
    if (user.getUsername() != null && !user.getUsername().isEmpty()) {
      foundUser.setUsername(user.getUsername());
    }
    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
      foundUser.setEmail(user.getEmail());
    }
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    User updatedUser = userRepository.save(foundUser);
    return new UserResponseDto(updatedUser);
  }

  public boolean deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }
}
