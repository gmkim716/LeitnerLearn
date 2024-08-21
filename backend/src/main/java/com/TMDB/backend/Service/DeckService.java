package com.TMDB.backend.Service;

import com.TMDB.backend.Dto.DeckDto;
import com.TMDB.backend.Entity.Deck;
import com.TMDB.backend.Entity.User;
import com.TMDB.backend.Repository.DeckRepository;
import com.TMDB.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

  private final DeckRepository deckRepository;
  private final UserRepository userRepository;

  public DeckDto createDeck(Long userId, String deckName) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    Deck deck = Deck.builder().name(deckName).user(user).build();
    return new DeckDto(deckRepository.save(deck));
  }

  public List<DeckDto> getAllDecksByUserId(Long userId) {
    List<Deck> decks = deckRepository.findAllByUserId(userId);
    return decks.stream().map((DeckDto::new)).toList();
  }

  public Deck getDeckById(Long deckId) {
    return deckRepository.findById(deckId)
      .orElseThrow(() -> new RuntimeException("덱을 찾을 수 없습니다."));
  }

  public Deck updateDeck(Long deckId, String deckName) {
    Deck deck = deckRepository.findById(deckId)
      .orElseThrow(() -> new RuntimeException("덱을 찾을 수 없습니다."));
    deck.setName(deckName);
    return deckRepository.save(deck);
  }

  public boolean deleteDeck(Long deckId) {
    Deck deck = deckRepository.findById(deckId)
      .orElseThrow(() -> new RuntimeException("덱을 찾을 수 없습니다."));
    deckRepository.delete(deck);
    return true;
  }
}
