package com.LeitnerLearn.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LearningLevelStatsDto {
  private int level01Counts;
  private int level02Counts;
  private int level03Counts;
  private int level04Counts;
}
