package com.TMDB.backend.Repository;

import com.TMDB.backend.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findByDeckId(Long deckId);
  long countByDeckIdAndNextReviewAtBefore(Long deckId, LocalDateTime now);
}
