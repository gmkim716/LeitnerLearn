package com.LeitnerLearn.backend.Dto;

import lombok.Data;

@Data
public class LoginRequestDto {

  private String username;
  private String password;
}
