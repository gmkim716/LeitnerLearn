package com.TMDB.backend.Service;


import com.TMDB.backend.Entity.Box;
import com.TMDB.backend.Entity.Card;
import com.TMDB.backend.Entity.Deck;
import com.TMDB.backend.Repository.BoxRepository;
import com.TMDB.backend.Repository.CardRepository;
import com.TMDB.backend.Repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final DeckRepository deckRepository;
  private final BoxRepository boxRepository;

  // 카드를 생성한다
  public Card createCard(Long deckId, String term, String definition, String exampleSentence) {
    Deck deck = deckRepository.findById(deckId).orElseThrow(() -> new RuntimeException("Deck을 찾을 수 없습니다"));
    // 첫 번째 Box를 가져온다
    Box firstBox = boxRepository.findByBoxNumber(1).orElseThrow(() -> new RuntimeException("Box를 찾을 수 없습니다"));

    Card card = Card.builder()
      .term(term)
      .definition(definition)
      .exampleSentence(exampleSentence)
      .deck(deck)
      .box(firstBox)
      .nextReviewAt(LocalDateTime.now().plusDays(1))  // 첫 복습일을 다음 날로 설정
      .build();

    return cardRepository.save(card);
  }

  // 카드 리스트를 조회한다
  public List<Card> getCardsByDeck(Long deckId) {
    return cardRepository.findByDeckId(deckId);
  }

  // 카드를 조회한다
  public Card getCardById(Long cardId) {
    return cardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card를 찾을 수 없습니다"));
  }

  // 카드 내용을 수정한다
  public Card updateCard(Long cardId, String term, String definition, String exampleSentence) {
    Card card = cardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card를 찾을 수 없습니다"));

    card.setTerm(term);
    card.setDefinition(definition);
    card.setExampleSentence(exampleSentence);
    return cardRepository.save(card);
  }

  // 카드를 삭제한다
  public boolean deleteCard(Long cardId) {
    if (cardRepository.existsById(cardId)) {
      cardRepository.deleteById(cardId);
      return true;
    } else {
      return false;
    }
  }
}