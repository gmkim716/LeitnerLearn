package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Dto.LearningCardDto;
import com.LeitnerLearn.backend.Entity.*;
import com.LeitnerLearn.backend.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {

  private final UserRepository userRepository;
  private final ReviewCardRepository reviewCardRepository;
  private final BoxRepository boxRepository;
  private final GlobalLearningCardRepository globalLearningCardRepository;

  // 카드 학습 결과를 업데이트 한다
  @Transactional
  public void updateLearningCardResult(Long cardId, Long userId, boolean correct) {
    // 학습 카드 여부 확인
    boolean isReviewCard = reviewCardRepository.findByGlobalLearningCardIdAndUserId(cardId, userId).isPresent();

    if (correct) {  // 첫 학습에 정답을 맞힌 경우
      if (isReviewCard) {
        moveCardToNextBox(cardId, userId);
      } else {
        saveNewReviewCard(cardId, userId, 5); // 장기 기억으로 저장
      }
    } else {  // 첫 학습에 틀린 경우
      if (isReviewCard) {
        moveCardToPrevBox(cardId, userId);
      } else {
        saveNewReviewCard(cardId, userId, 1); // 복습 상자로 저장
      }
    }
  }

  private void saveNewReviewCard(Long cardId, Long userId, int boxNumber) {
    log.info("########### userId: " + userId);

    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
    GlobalLearningCard newCard = globalLearningCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("교육 데이터를 조회할 수 없습니다"));
    Box box = boxRepository.findByBoxNumber(boxNumber).orElseThrow(() -> new RuntimeException("상자를 찾을 수 없습니다"));

    LocalDateTime nextReviewAt;
    if (boxNumber == 5) {  // 처음 본 문제를 맞힌 경우
      nextReviewAt = LocalDateTime.now().plusDays(9999);
    } else {
      nextReviewAt = LocalDateTime.now();
    }

    ReviewCard reviewCard = ReviewCard.builder()
      .term(newCard.getTerm())
      .definition(newCard.getDefinition())
      .exampleSentence(newCard.getExampleSentence())
      .nextReviewAt(nextReviewAt)
      .user(user)
      .globalLearningCard(newCard)
      .box(box)
      .difficultyLevel(newCard.getDifficultyLevel())
      .build();

    reviewCardRepository.save(reviewCard);
  }

  private void moveCardToNextBox(Long cardId, Long userId) {

    log.info("########### moveCardToNextBox에 사용된 값" + " cardId : " + cardId + " userId : " + userId);
    ReviewCard reviewCard = reviewCardRepository.findByGlobalLearningCardIdAndUserId(cardId, userId)
      .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다"));
    Box nextBox = boxRepository.findByBoxNumber(reviewCard.getBox().getBoxNumber() + 1)
      .orElseThrow(() -> new RuntimeException("다음 카드 상자를 찾을 수 없습니다"));

    reviewCard.promoteToNextBox(nextBox);

    reviewCardRepository.save(reviewCard);
  }

  private void moveCardToPrevBox(Long cardId, Long userId) {
    ReviewCard reviewCard = reviewCardRepository.findByGlobalLearningCardIdAndUserId(cardId, userId)
      .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다"));
    Box prevBox = boxRepository.findByBoxNumber(Math.max(reviewCard.getBox().getBoxNumber() - 1, 1))
      .orElseThrow(() -> new RuntimeException("다음 카드 상자를 찾을 수 없습니다"));

    reviewCard.promoteToPrevBox(prevBox);

    reviewCardRepository.save(reviewCard);
  }

  @Transactional(readOnly = true)
  public LearningCardDto makeLearningCards(Long userId) {
    // 복습 가능한 카드를 조회한다
    LocalDateTime now = LocalDateTime.now();
    List<ReviewCard> reviewCards = reviewCardRepository.findByUserIdAndNextReviewAtBefore(userId, now);
    int reviewCardsCount = reviewCards.size();

    // 새롭게 학습할 카드를 추가한다
    Pageable pageable = PageRequest.of(0, 30 - reviewCardsCount);  // 총 30개의 개수가 될 수 있도록 reviewCards에서 나머지 개수만큼 구함
//    Page<GlobalLearningCard> newCards = globalLearningCardRepository.findAll(pageable);  // user의 복습 카드에 없는 항목을 조회해야 함

    Page<GlobalLearningCard> newCards = globalLearningCardRepository.findAllExcludingUserReviewCards(userId, pageable);

    // 복습 카드와 새로운 카드를 섞어서 학습 덱을 생성한다
    List<Object> shuffledCards = new ArrayList<>();
    shuffledCards.addAll(reviewCards);
    shuffledCards.addAll(newCards.getContent());
    Collections.shuffle(shuffledCards);

    // 통계 데이터
    int newCardsCount = newCards.getSize();
    int totalCardsCount = shuffledCards.size();

    return LearningCardDto.builder()
      .reviewCardsCount(reviewCardsCount)
      .newCardsCount(newCardsCount)
      .totalCardsCount(totalCardsCount)
      .reviewCards(reviewCards)
      .newCards(newCards.getContent())
      .shuffledCards(shuffledCards)
      .build();
  }
}
