package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {

  private Long id;
  private String username;
  private String password;
  private String email;
  private Role role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
