package com.LeitnerLearn.backend.Entity;

import com.LeitnerLearn.backend.Entity.common.BaseCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "review_cards")
@Setter @Getter @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
public class ReviewCard extends BaseCard {

  private LocalDateTime nextReviewAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "box_number") @JsonIgnore
  private Box box;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id") @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id") @JsonIgnore
  private GlobalLearningCard globalLearningCard;

  // 다음 박스로 이동한다
  public void promoteToNextBox(Box nextBox) {
    setBox(nextBox);
    updateNextReviewDate(true);
  }

  // 이전 박스로 이동한다
  public void promoteToPrevBox(Box prevBox) {
    setBox(prevBox);
    updateNextReviewDate(false);
  }


  // 다음 복습 날짜를 설정한다
  public void updateNextReviewDate(boolean isCorrect) {
    if (box != null) {  // 박스가 설정되어 있으면 다음 복습 날짜를 설정한다
      if (isCorrect) {
        this.nextReviewAt = this.getNextReviewAt().plusDays(box.getReviewIntervalDays());
      } else {
        this.nextReviewAt = LocalDateTime.now();
      }
    }
  }
}