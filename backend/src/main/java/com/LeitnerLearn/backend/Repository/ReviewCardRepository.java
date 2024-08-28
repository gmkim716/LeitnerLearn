package com.LeitnerLearn.backend.Repository;

import com.LeitnerLearn.backend.Entity.Review;
import com.LeitnerLearn.backend.Entity.ReviewCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewCardRepository extends JpaRepository<ReviewCard, Long> {
  List<ReviewCard> findByUserId(Long userId);  // 사용자의 전체 복습카드를 조회한다
  List<ReviewCard> findByUserIdAndNextReviewAtBefore(Long userId, LocalDateTime now);  // 사용자가 복습해야 하는 카드를 조회한다
//  Optional<ReviewCard> findByIdAndUserId(Long cardId, Long userId);

  Optional<ReviewCard> findByGlobalLearningCardId(Long cardId);

  Optional<ReviewCard> findByGlobalLearningCardIdAndUserId(Long cardId, Long userId);
}
