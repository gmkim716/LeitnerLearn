package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Entity.Role;
import com.LeitnerLearn.backend.Entity.User;
import com.LeitnerLearn.backend.Exception.UserNotFoundException;
import com.LeitnerLearn.backend.Exception.UsernameAlreadyExistsException;
import com.LeitnerLearn.backend.Repository.UserRepository;
import com.LeitnerLearn.backend.Dto.UserRequestDto;
import com.LeitnerLearn.backend.Dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public ResponseEntity<UserResponseDto> registerUser(UserRequestDto userDto) {
    validateUserDetails(userDto);

    User user = User.builder()
      .username(userDto.getUsername())
      .email(userDto.getEmail())
      .password(passwordEncoder.encode(userDto.getPassword()))
      .role(Role.USER_BEGINNER)
      .createdAt(LocalDateTime.now())
      .updatedAt(LocalDateTime.now())
      .build();

    User savedUser = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(savedUser));
  }

  @Transactional
  public UserResponseDto updateUser(Long userId, UserRequestDto userDto) {
    User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

    validateUserDetails(userDto, foundUser);

    if (userDto.getUsername() != null && !userDto.getUsername().isEmpty()) {
      foundUser.setUsername(userDto.getUsername());
    }
    if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
      foundUser.setEmail(userDto.getEmail());
    }
    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
      foundUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

    User updatedUser = userRepository.save(foundUser);
    return new UserResponseDto(updatedUser);
  }

  @Transactional
  public boolean deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }

  @Transactional
  public UserResponseDto updateUserRole(Long id, Role role) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    user.setRole(role);
    userRepository.save(user);
    return new UserResponseDto(user);
  }

  // 유효성 검사 메서드
  private void validateUserDetails(UserRequestDto userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
      throw new UsernameAlreadyExistsException("이미 사용 중인 사용자 이름입니다");
    }
    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new UsernameAlreadyExistsException("이미 사용 중인 이메일입니다");
    }

    if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
      throw new IllegalArgumentException("사용자 이름은 필수 입력 항목입니다");
    }
    if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
      throw new IllegalArgumentException("비밀번호는 필수 입력 항목입니다");
    }
    if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
      throw new IllegalArgumentException("이메일은 필수 입력 항목입니다");
    }
  }

  // 기존 사용자에 대해 유효성 검사 메서드 오버로드
  private void validateUserDetails(UserRequestDto userDto, User existingUser) {
    if (userDto.getUsername() != null && !userDto.getUsername().isEmpty()) {
      if (userRepository.existsByUsername(userDto.getUsername()) && !existingUser.getUsername().equals(userDto.getUsername())) {
        throw new UsernameAlreadyExistsException("이미 사용 중인 사용자 이름입니다");
      }
    }
    if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
      if (userRepository.existsByEmail(userDto.getEmail()) && !existingUser.getEmail().equals(userDto.getEmail())) {
        throw new UsernameAlreadyExistsException("이미 사용 중인 이메일입니다");
      }
    }

    if (userDto.getUsername() == null || !userDto.getUsername().isEmpty()) {
      throw new IllegalArgumentException("사용자 이름은 필수 입력 항목입니다");
    }
    if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
      throw new IllegalArgumentException("비밀번호는 필수 입력 항목입니다");
    }
    if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
      throw new IllegalArgumentException("이메일은 필수 입력 항목입니다");
    }
  }
}
