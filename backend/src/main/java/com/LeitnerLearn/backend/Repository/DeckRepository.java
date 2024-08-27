package com.LeitnerLearn.backend.Repository;

import com.LeitnerLearn.backend.Entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
  List<Deck> findAllByUserId(Long userId);

}
