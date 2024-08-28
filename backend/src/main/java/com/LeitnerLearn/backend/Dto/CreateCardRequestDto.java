package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import lombok.Data;

@Data
public class CreateCardRequestDto {
  private Long userId;
  private String term;
  private String definition;
  private String exampleSentence;
  private DifficultyLevel difficultyLevel;
}
