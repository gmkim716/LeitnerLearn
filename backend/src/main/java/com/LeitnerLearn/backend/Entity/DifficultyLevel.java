package com.LeitnerLearn.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DifficultyLevel {
  BEGINNER("01"), INTERMEDIATE("02"), ADVANCED("03"), EXPERT("04");

  private final String code;
}
