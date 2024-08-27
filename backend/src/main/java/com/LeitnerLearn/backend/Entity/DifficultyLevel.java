package com.LeitnerLearn.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DifficultyLevel {
  BEGINNER("01"), INTERMEDIATE("02"), ADVANCED("03");

  private final String code;

  public static DifficultyLevel fromCode(String code) {
    for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
      if (difficultyLevel.getCode().equals(code)) {
        return difficultyLevel;
      }
    }
    throw new IllegalArgumentException("알 수 없는 code: " + code);
  }

}
