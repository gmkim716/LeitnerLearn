package com.LeitnerLearn.backend.Dto;

import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import com.LeitnerLearn.backend.Entity.ReviewCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LearningCardDto {
  private List<ReviewCard> reviewCards;  // 곧 지워질 필드입니다?
  private List<GlobalLearningCard> newCards;  // 곧 지워질 필드입니다?
  private List<Object> shuffledCards;  // reviewCards, newCards가 섞인 결과를 담는 필드
}
