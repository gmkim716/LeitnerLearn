package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StarterCardsDto {
  private int count;
  private List<GlobalLearningCard> cards;
}
