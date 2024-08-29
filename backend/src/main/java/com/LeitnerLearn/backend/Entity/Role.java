package com.LeitnerLearn.backend.Entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
  ADMIN("99"),
  USER_STARTER("00"),
  USER_BEGINNER("01"),
  USER_INTERMEDIATE("02"),
  USER_ADVANCED("03"),
  USER_EXPERT("04");

  private String code;

  Role(String code) {
    this.code = code;
  }
}
