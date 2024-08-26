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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StudyService {

  private static final Logger log = LoggerFactory.getLogger(StudyService.class);
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
  @Transactional(readOnly = true)
  public List<Card> getCardsForReview(Long deckId) {
    return cardRepository.findByDeckId(deckId);
  }

  // 카드 복습 결과를 업데이트 한다
  public void reviewCard(Long cardId, boolean correct) {
    Card card = cardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card not found"));

    Box currentBox = card.getBox();
    Box nextBox;

    if (correct) {  // 정답을 맞췄을 때
      if (currentBox.getBoxNumber() < 5) {
        nextBox = boxRepository.findByBoxNumber(currentBox.getBoxNumber() + 1)  // 다음 Box로 이동한다
          .orElseThrow(() -> new RuntimeException("Box not found"));
      } else {
        nextBox = currentBox;  // Box 5에서는 이동하지 않는다 (장기기억 완료)
      }
    } else {  // 정답을 틀렸을 때
      nextBox = boxRepository.findByBoxNumber(Math.max(currentBox.getBoxNumber() - 1, 1))  // 이전 Box로 이동한다, 1보다 작아지지 않도록 한다
        .orElseThrow(() -> new RuntimeException("Box not found"));
    }

    card.setBox(nextBox);
    card.updateNextReviewDate();
    cardRepository.save(card);
  }

  // 덱의 학습 상태를 조회한다
  @Transactional(readOnly = true)
  public String getStudyStatus(Long deckId) {
    long reviewCards = cardRepository.countByDeckIdAndNextReviewAtBefore(deckId, LocalDateTime.now());
    return "복습이 필요한 카드 수: " + reviewCards;
  }

  // 복습 가능한 목록을 조회한다
  @Transactional(readOnly = true)
  public List<Card> getReviewCards(Long deckId) {
    return cardRepository.findByDeckIdAndNextReviewAtBefore(deckId, LocalDateTime.now());
  }

  // on developing... 8/26
  // 자동으로 학습 목록을 생성한다
  @Transactional(readOnly = true)
  public List<Card> makeLearningDeckWithDeckId(Long deckId) {
    List<Card> reviewCards = getReviewCards(deckId);
    List<Card> newCards = cardRepository.findByDeckIdAndNextReviewAtIsNull(deckId);

    if (reviewCards.size() < 5) {
      int newCardCount = 5 - reviewCards.size();
      List<Card> additionalCards = cardRepository.findTopNByDeckIdAndNextReviewAtIsNotNullOrderByNextReviewAtAsc(deckId, newCardCount);
      newCards.addAll(additionalCards);
    }

    return newCards;
  }

}
