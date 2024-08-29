package com.LeitnerLearn.backend.Repository;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalLearningCardRepository extends JpaRepository<GlobalLearningCard, Long> {
  Page<GlobalLearningCard> findAll(Pageable pageable);

  @Query("SELECT g FROM GlobalLearningCard g WHERE g.id NOT IN " +
    "(SELECT r.globalLearningCard.id FROM ReviewCard r WHERE r.user.id = :userId)")
  Page<GlobalLearningCard> findAllExcludingUserReviewCards(@Param("userId") Long userId, Pageable pageable);

  @Query("SELECT g FROM GlobalLearningCard g WHERE g.id NOT IN " +
    "(SELECT r.globalLearningCard.id FROM ReviewCard r WHERE r.user.id = :userId) " +
    "AND g.id NOT IN :userStudiedCardIds " +
    "AND g.difficultyLevel = 'STARTER'")
  List<GlobalLearningCard> findAvailableStaterCardsForUser(Long userId, List<Long> userStudiedCardIds);

  List<GlobalLearningCard> findAllByDifficultyLevel(DifficultyLevel difficultyLevel);
  Page<GlobalLearningCard> findAllByDifficultyLevel(DifficultyLevel difficultyLevel, Pageable pageable);

}
