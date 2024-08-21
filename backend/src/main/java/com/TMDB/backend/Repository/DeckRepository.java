package com.TMDB.backend.Repository;

import com.TMDB.backend.Entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
  List<Deck> findAllByUserId(Long userId);

}