package com.LeitnerLearn.backend.Service;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import com.LeitnerLearn.backend.Repository.GlobalLearningCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalLearningCardService {

  private final GlobalLearningCardRepository globalLearningCardRepository;

  // 학습 레벨에 맞는 global 학습 카드 목록을 가져온다
  @Transactional(readOnly = true)
  public List<GlobalLearningCard> getGlobalLearningCardsByDifficultyLevel(DifficultyLevel levelCode) {
    return globalLearningCardRepository.findAllByDifficultyLevel(levelCode);
  }

//  // 학습 레벨에 맞는 global 학습 카드 목록을 가져온다 + 사용자의 deck에 추가 되지 않은 카드만 가져온다
//  @Transactional(readOnly = true)
//  public List<GlobalLearningCard> getGlobalLearningCardsByDifficultyLevelAndNotInUserDeck(DifficultyLevel levelCode, Long deckId) {
//    return globalLearningCardRepository.findAllByDifficultyLevelAndNotInUserDeck(levelCode, deckId);
//  }

}
