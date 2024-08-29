package com.LeitnerLearn.backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearningStatsDto {
  private Long userId;
  private String username;
  private CardIdListDto reviewingCardIds;
  private CardIdListDto longTermMemoryCardIds;
  private LearningLevelStatsDto learningLevelStats;
}

