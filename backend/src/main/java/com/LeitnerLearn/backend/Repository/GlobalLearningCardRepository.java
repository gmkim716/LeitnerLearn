package com.LeitnerLearn.backend.Repository;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalLearningCardRepository extends JpaRepository<GlobalLearningCard, Long> {
  List<GlobalLearningCard> findAllByDifficultyLevel(DifficultyLevel difficultyLevel);
  Page<GlobalLearningCard> findAllByDifficultyLevel(DifficultyLevel difficultyLevel, Pageable pageable);
}
