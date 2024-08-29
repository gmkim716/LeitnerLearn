package com.LeitnerLearn.backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EntityListeners(AuditingEntityListener.class)  // @CreatedDate, @LastModifiedDate의 감사(Auditing) 기능을 활성화 하기 위한 어노테이션
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role = Role.USER;

  @Column(nullable = false)
  private Integer level = 1;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @ElementCollection  // Check. 정리하기
  @CollectionTable(name = "user_reviewing_card_ids", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "card_id")
  private List<Long> reviewCardIds = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "user_long_term_memory_card_ids", joinColumns = @JoinColumn(name = "user_id"))
  private List<Long> longTermMemoryCardIds = new ArrayList<>();

  public void addReviewCardId(Long reviewCardId) {
    this.reviewCardIds.add(reviewCardId);
  }

  public void removeReviewCardId(Long reviewCardId) {
    this.reviewCardIds.remove(reviewCardId);
  }

  public void addLongTermMemoryCardId(Long longTermMemoryCardId) {
    if (!this.longTermMemoryCardIds.contains(longTermMemoryCardId)) {
      this.longTermMemoryCardIds.add(longTermMemoryCardId);
    }
  }

  public void removeLongTermMemoryCardId(Long cardId) {
    if (this.longTermMemoryCardIds.remove(cardId)) {
      this.addReviewCardId(cardId);
    }
  }
}
