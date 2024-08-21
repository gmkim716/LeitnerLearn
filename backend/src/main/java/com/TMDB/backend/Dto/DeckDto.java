package com.TMDB.backend.Dto;

import com.TMDB.backend.Entity.Card;
import com.TMDB.backend.Entity.Deck;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DeckDto {

  private Long id;
  private String name;
  private List<Long> cardIds;
  private Long userId;

  public DeckDto(Deck deck) {
    this.id = deck.getId();
    this.name = deck.getName();
    this.cardIds = deck.getCards().stream().map(Card::getId).collect(Collectors.toList());
    this.userId = deck.getUser() != null ? deck.getUser().getId() : null;
  }
}
