package com.TMDB.backend.Dto;

import com.TMDB.backend.Entity.Card;
import lombok.Data;

import java.util.List;

@Data
public class DeckResponseDto {
  private Long id;
  private String name;
  private List<Card> cards;
}
