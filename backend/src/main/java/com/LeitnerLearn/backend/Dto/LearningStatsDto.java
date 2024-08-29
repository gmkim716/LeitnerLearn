package com.LeitnerLearn.backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearningStatsDto {
  private Long userId;
  private String username;
  private int totalReviewCardsCount;
  private LearningLevelStatsDto learningLevelStats;
}

