package com.TMDB.backend.Service;

import com.TMDB.backend.Entity.Box;
import com.TMDB.backend.Entity.Card;
import com.TMDB.backend.Entity.Deck;
import com.TMDB.backend.Entity.User;
import com.TMDB.backend.Repository.BoxRepository;
import com.TMDB.backend.Repository.CardRepository;
import com.TMDB.backend.Repository.DeckRepository;
import com.TMDB.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StudyService {

  private final CardRepository cardRepository;
  private final DeckRepository deckRepository;
  private final UserRepository userRepository;
  private final BoxRepository boxRepository;

  public Deck assignDeckBasedOnDifficulty(Long userId, String difficulty) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    Map<String, List<String>> difficultyWordMap = Map.of(
      "Beginner", List.of("Apple", "Banana", "Cat"),
      "Intermediate", List.of("Develop", "Structure", "Concept"),
      "Advanced", List.of("Synthesize", "Phenomenon", "Elaborate")
    );

    List<String> terms = difficultyWordMap.getOrDefault(difficulty, List.of());

    Deck deck = new Deck();
    deck.setName(difficulty + " Vocabulary");
    deck.setUser(user);
    deck = deckRepository.save(deck);

    Box firstBox = boxRepository.findByBoxNumber(1)
      .orElseThrow(() -> new RuntimeException("Box not found"));

    for (String term : terms) {
      Card card = Card.builder()
        .term(term)
        .definition("Definition of " + term)
        .deck(deck)
        .box(firstBox)
        .build();
      card.updateNextReviewDate();  // 다음 복습 날짜를 설정한다
      cardRepository.save(card);
    }

    return deck;
  }

  // 복습할 카드 목록을 가져온다
  public List<Card> getCardsForReview(Long deckId) {
    return cardRepository.findByDeckId(deckId);
  }

  // 가드 복습 결과를 업데이트 한다
  public void reviewCard(Long cardId, boolean correct) {
    Card card = cardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card not found"));

    Box currentBox = card.getBox();
    Box nextBox;

    if (correct) {  // 정답을 맞췄을 때
      nextBox = boxRepository.findByBoxNumber(currentBox.getBoxNumber() + 1)  // 다음 Box로 이동한다
        .orElseThrow(() -> new RuntimeException("Box not found"));
    } else {  // 정답을 틀렸을 때
      nextBox = boxRepository.findByBoxNumber(Math.max(currentBox.getBoxNumber() - 1, 1))  // 이전 Box로 이동한다, 1보다 작아지지 않도록 한다
        .orElseThrow(() -> new RuntimeException("Box not found"));
    }

    card.setBox(nextBox);
    card.updateNextReviewDate();
    cardRepository.save(card);
  }

  // 학습 상태를 확인한다
  public String getStudyStatus(Long deckId) {
    long reviewCards = cardRepository.countByDeckIdAndNextReviewAtBefore(deckId, LocalDateTime.now());
    return "복습이 필요한 카드 수: " + reviewCards;
  }
}