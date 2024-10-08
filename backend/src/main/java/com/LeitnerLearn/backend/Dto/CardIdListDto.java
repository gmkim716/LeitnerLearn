package com.LeitnerLearn.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class CardIdListDto {

  private int count;
  private List<Long> ids;
}
