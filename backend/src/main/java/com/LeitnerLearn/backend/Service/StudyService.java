package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Entity.*;
import com.LeitnerLearn.backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class StudyService {

  private final ReviewCardRepository reviewCardRepository;
  private final BoxRepository boxRepository;
  private final GlobalLearningCardRepository globalLearningCardRepository;
//
//  // 복습할 카드 목록을 가져온다
//  @Transactional(readOnly = true)
//  public List<ReviewCard> getCardsForReview(Long deckId) {
//    return cardRepository.findByDeckId(deckId);
//  }

  // 카드 복습 결과를 업데이트 한다
  public void reviewCard(Long cardId, boolean correct) {
    ReviewCard card = reviewCardRepository.findById(cardId)
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
    reviewCardRepository.save(card);
  }

//  // 덱의 학습 상태를 조회한다
//  @Transactional(readOnly = true)
//  public String getStudyStatus(Long deckId) {
//    int reviewCards = cardRepository.countByDeckIdAndNextReviewAtBefore(deckId, LocalDateTime.now());
//    return "복습이 필요한 카드 수: " + reviewCards;
//  }

//  // 복습 가능한 목록을 조회한다
//  @Transactional(readOnly = true)
//  public List<ReviewCard> getReviewCards(Long deckId) {
//    return cardRepository.findByDeckIdAndNextReviewAtBefore(deckId, LocalDateTime.now());
//  }

  // global 학습 목록에서 difficultyLevel에 해당하는 cnt 개의 데이터를 조회한다
  @Transactional(readOnly = true)
  public List<GlobalLearningCard> getGlobalLearningCardsByDifficultyLevel(DifficultyLevel difficultyLevel, int count) {
    Pageable pageable = PageRequest.of(0, count);
    Page<GlobalLearningCard> pageResult = globalLearningCardRepository.findAllByDifficultyLevel(difficultyLevel, pageable);
    return pageResult.getContent();
  }

//  @Transactional(readOnly = true)
//  public LearningCardDto makeLearningCards(Long deckId, DifficultyLevel difficultyLevel, int size) {
//    List<ReviewCard> reviewCards = getReviewCards(deckId); // 복습 가능한 카드 조회
//    int reviewCardsCount = reviewCards.size();  // 복습 가능한 카드의 수 조회
//
//    // 레벨에 맞는 global 학습 카드 조회
//    List<GlobalLearningCard> newCards = getGlobalLearningCardsByDifficultyLevel(difficultyLevel, size - reviewCardsCount);
//
//    // 무작위 순서로 섞인 객체를 생성
//    List<Object> combinedCards = new ArrayList<>();
//    combinedCards.addAll(reviewCards);
//    combinedCards.addAll(newCards);
//    Collections.shuffle(combinedCards);
//
//    return new LearningCardDto(reviewCards, newCards, combinedCards);
//  }

//
//  @Transactional(readOnly = true)
//  public List<Card> makeLearningCards(Long deckId) {
//    List<Card> reviewCards = getReviewCards(deckId);  // 복습 가능한 카드 조회
//    int reviewCardsCount = reviewCards.size();
//
//    Pageable pageable<GlobalLearningCard> globalLearningCards = globalLearningCardRepository.findAll();
//
//
//    if (reviewCards.size() < 5) {
//      int newCardCount = 5 - reviewCards.size();
//      List<Card> additionalCards = cardRepository.findTopNByDeckIdAndNextReviewAtIsNotNullOrderByNextReviewAtAsc(deckId, newCardCount);
//      newCards.addAll(additionalCards);
//    }
//
//    return newCards;
//  }




//  // 레벨에 맞는 global_leaning_card 리스트를 조회한다
//  @Transactional(readOnly = true)
//  public List<GlobalLearningCard> getGlobalLearningCardsByDifficultyLevel(DifficultyLevel difficultyLevel) {
//    return globalLearningCardRepository.findAllByDifficultyLevel(difficultyLevel);
//  }

//  // 사용자의 복습 목록과 함께 새로운 단어들을 추가해서 학습 덱(카드 목록)을 생성한다
//  @Transactional(readOnly = true)
//  public List<Card> makeLearningDeckWithDeckId(Long deckId) {
//    List<Card> reviewCards = getReviewCards(deckId);
//    List<Card> newCards = cardRepository.findByDeckIdAndNextReviewAtIsNull(deckId);
//
//    if (reviewCards.size() < 5) {
//      int newCardCount = 5 - reviewCards.size();
//      List<Card> additionalCards = cardRepository.findTopNByDeckIdAndNextReviewAtIsNotNullOrderByNextReviewAtAsc(deckId, newCardCount);
//      newCards.addAll(additionalCards);
//    }
//
//    return newCards;
//  }



}
