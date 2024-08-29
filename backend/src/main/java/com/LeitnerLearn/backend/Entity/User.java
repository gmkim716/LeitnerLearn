package com.LeitnerLearn.backend.Entity;

import com.LeitnerLearn.backend.Dto.CardIdListDto;
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
  private Role role = Role.USER_STARTER;

  @Column(nullable = false)
  private Integer level = 1;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;


  @ElementCollection // Check. JsonIgnore, 직렬화에 대해 정리하기
  @CollectionTable(name = "user_reviewing_card_ids", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "card_id")
  private List<Long> reviewingCardIds = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "user_long_term_memory_card_ids", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "card_id")
  private List<Long> longTermMemoryCardIds = new ArrayList<>();


  @Transient  // Transient를 사용해 데이터베이스에 저장되지 않도록 한다
  public CardIdListDto getReviewingCardIds() {
    return new CardIdListDto(this.reviewingCardIds.size(), this.reviewingCardIds);
  }

  @Transient
  public CardIdListDto getLongTermMemoryCardIds() {
    return new CardIdListDto(this.longTermMemoryCardIds.size(), this.longTermMemoryCardIds);
  }

  public void addReviewCardId(Long reviewCardId) {
    this.reviewingCardIds.add(reviewCardId);
  }

  public void removeReviewCardId(Long reviewCardId) {
    this.reviewingCardIds.remove(reviewCardId);
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
