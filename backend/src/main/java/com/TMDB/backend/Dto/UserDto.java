package com.TMDB.backend.Dto;

import com.TMDB.backend.Entity.Role;
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
  private List<Long> deckIds;
  private List<Long> achievementIds;

}
