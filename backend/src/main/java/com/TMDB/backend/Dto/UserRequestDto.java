package com.TMDB.backend.Dto;

import lombok.Data;

@Data
public class UserRequestDto {

  private String username;
  private String email;
  private String password;

}