package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.Achievement;
import com.LeitnerLearn.backend.Entity.Deck;
import com.LeitnerLearn.backend.Entity.Role;
import com.LeitnerLearn.backend.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDto {

  private Long id;
  private String username;
  private String email;
  private String password;
  private Role role;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<Long> deckIds;
  private List<Long> achievementIds;

  public UserResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.role = user.getRole();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.deckIds = user.getDecks().stream().map(Deck::getId).toList();
    this.achievementIds = user.getAchievements().stream().map(Achievement::getId).toList();
  }
}
