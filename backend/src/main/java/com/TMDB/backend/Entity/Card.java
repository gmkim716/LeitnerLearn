package com.TMDB.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String term;

  @Column(nullable = false)
  private String definition;

  private String exampleSentence;
  private LocalDateTime nextReviewAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "box_number") @JsonIgnore
  private Box box;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "deck_id") @JsonIgnore
  private Deck deck;

  @ManyToMany
  @JoinTable(
    name = "card_tags",
    joinColumns = @JoinColumn(name = "card_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

  // 다음 복습 날짜를 설정한다
  public void updateNextReviewDate() {
    if (box != null) {  // 박스가 설정되어 있으면 다음 복습 날짜를 설정한다
      this.nextReviewAt = LocalDateTime.now().plusDays(box.getReviewIntervalDays());
    }
  }

  // 학습자가 맞췄을 때 다음 Box로 이동한다
  public void promoteToNextBox(Box nextBox) {
    setBox(nextBox);
    updateNextReviewDate();
  }
}