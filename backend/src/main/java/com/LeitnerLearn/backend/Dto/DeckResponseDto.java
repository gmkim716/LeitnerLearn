package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.Card;
import lombok.Data;

import java.util.List;

@Data
public class DeckResponseDto {
  private Long id;
  private String name;
  private List<Card> cards;
}
