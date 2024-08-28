package com.LeitnerLearn.backend.Repository;

import com.LeitnerLearn.backend.Entity.ReviewCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCardRepository extends JpaRepository<ReviewCard, Long> {
  List<ReviewCard> findByUserId(Long userId);
//  List<ReviewCard> findByDeckId(Long deckId);
//  int countByDeckIdAndNextReviewAtBefore(Long deckId, LocalDateTime now);
//  List<ReviewCard> findByDeckIdAndNextReviewAtBefore(Long deckId, LocalDateTime now);
}
