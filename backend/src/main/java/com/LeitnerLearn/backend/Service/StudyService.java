package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Dto.*;
import com.LeitnerLearn.backend.Entity.*;
import com.LeitnerLearn.backend.Exception.UserNotFoundException;
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
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
    GlobalLearningCard newCard = globalLearningCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("교육 데이터를 조회할 수 없습니다"));
    Box box = boxRepository.findByBoxNumber(boxNumber).orElseThrow(() -> new RuntimeException("상자를 찾을 수 없습니다"));

    LocalDateTime nextReviewAt = (boxNumber == 5) ? LocalDateTime.now().plusDays(9999) : LocalDateTime.now();

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

    if (boxNumber == 5) {
      user.addLongTermMemoryCardId(cardId);
    } else {
      user.addReviewCardId(cardId);
    }
    userRepository.save(user);
  }

  private void moveCardToNextBox(Long cardId, Long userId) {
    ReviewCard reviewCard = reviewCardRepository.findByGlobalLearningCardIdAndUserId(cardId, userId).orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다"));
    Box nextBox = boxRepository.findByBoxNumber(reviewCard.getBox().getBoxNumber() + 1).orElseThrow(() -> new RuntimeException("다음 카드 상자를 찾을 수 없습니다"));

    reviewCard.promoteToNextBox(nextBox);
    reviewCardRepository.save(reviewCard);

    if (nextBox.getBoxNumber() == 5) {
      User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
      user.removeReviewCardId(cardId);
      user.addLongTermMemoryCardId(cardId);
      userRepository.save(user);
    }
  }

  private void moveCardToPrevBox(Long cardId, Long userId) {
    ReviewCard reviewCard = reviewCardRepository.findByGlobalLearningCardIdAndUserId(cardId, userId).orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다"));
    Box prevBox = boxRepository.findByBoxNumber(Math.max(reviewCard.getBox().getBoxNumber() - 1, 1)).orElseThrow(() -> new RuntimeException("다음 카드 상자를 찾을 수 없습니다"));

    reviewCard.promoteToPrevBox(prevBox);
    reviewCardRepository.save(reviewCard);

    if (reviewCard.getBox().getBoxNumber() == 5) {
      User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
      user.removeLongTermMemoryCardId(cardId);
      user.addReviewCardId(cardId);
      userRepository.save(user);
    }
  }

  @Transactional(readOnly = true)
  public LearningCardDto makeLearningCards(Long userId) {
    // 복습 가능한 카드를 조회한다
    LocalDateTime now = LocalDateTime.now();
    List<ReviewCard> reviewCards = reviewCardRepository.findByUserIdAndNextReviewAtBefore(userId, now);
    int reviewCardsCount = reviewCards.size();

    // 새롭게 학습할 카드를 추가한다
    Pageable pageable = PageRequest.of(0, 30 - reviewCardsCount);  // 총 30개의 개수가 될 수 있도록 reviewCards에서 나머지 개수만큼 구함
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

  @Transactional(readOnly = true)
  public LearningStatsDto getLearningStats(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

    List<ReviewCard> reviewCards = reviewCardRepository.findByUserId(userId);

    CardIdListDto reviewingCardIds = user.getReviewingCardIds();
    CardIdListDto longTermMemoryCardIds = user.getLongTermMemoryCardIds();
    LearningLevelStatsDto levelStats = getLearningLevelStatsDto(reviewCards);

    return LearningStatsDto.builder()
      .userId(user.getId())
      .username(user.getUsername())
      .reviewingCardIds(reviewingCardIds)
      .longTermMemoryCardIds(longTermMemoryCardIds)
      .learningLevelStats(levelStats)
      .build();
  }

  private LearningLevelStatsDto getLearningLevelStatsDto(List<ReviewCard> reviewCards) {
    int level01Count = 0;
    int level02Count = 0;
    int level03Count = 0;
    int level04Count = 0;

    for (ReviewCard reviewCard : reviewCards) {
      switch (reviewCard.getDifficultyLevel()) {
        case BEGINNER -> level01Count++;
        case INTERMEDIATE -> level02Count++;
        case ADVANCED -> level03Count++;
        case EXPERT -> level04Count++;
      }
    }

    return new LearningLevelStatsDto(
      level01Count, level02Count, level03Count, level04Count
    );
  }

  @Transactional(readOnly = true)
  public StarterCardsDto getStarterCards(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

    List<Long> reviewingCardIds = user.getReviewingCardIds().getIds();
    List<Long> longTermMemoryCardIds = user.getLongTermMemoryCardIds().getIds();

    List<Long> studiedCardIds = new ArrayList<>();
    studiedCardIds.addAll(reviewingCardIds);
    studiedCardIds.addAll(longTermMemoryCardIds);

    List<GlobalLearningCard> starterCards = globalLearningCardRepository.findAvailableStaterCardsForUser(userId, studiedCardIds);
    int starterCardsCount = starterCards.size();

    return new StarterCardsDto(starterCardsCount, starterCards);
  }

}
