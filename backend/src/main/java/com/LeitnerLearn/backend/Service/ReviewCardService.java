package com.LeitnerLearn.backend.Service;


import com.LeitnerLearn.backend.Dto.CreateCardRequestDto;
import com.LeitnerLearn.backend.Entity.Box;
import com.LeitnerLearn.backend.Entity.ReviewCard;
import com.LeitnerLearn.backend.Entity.User;
import com.LeitnerLearn.backend.Repository.BoxRepository;
import com.LeitnerLearn.backend.Repository.ReviewCardRepository;
import com.LeitnerLearn.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewCardService {

  private final ReviewCardRepository reviewCardRepository;
  private final BoxRepository boxRepository;
  private final UserRepository userRepository;

  // 카드를 생성한다
  public ReviewCard createCard(CreateCardRequestDto requestDto) {
    Box firstBox = boxRepository.findByBoxNumber(1).orElseThrow(() -> new RuntimeException("Box를 찾을 수 없습니다"));
    User user = userRepository.findById(requestDto.getUserId())
      .orElseThrow(() -> new RuntimeException("User를 찾을 수 없습니다"));

    ReviewCard card = ReviewCard.builder()
      .term(requestDto.getTerm())
      .definition(requestDto.getDefinition())
      .exampleSentence(requestDto.getExampleSentence())
      .user(user)
      .box(firstBox)
      .difficultyLevel(requestDto.getDifficultyLevel())
      .nextReviewAt(LocalDateTime.now())  // 카드 등록 시점부터 복습에 활용 가능
      .build();

    return reviewCardRepository.save(card);
  }

  // 유저의 복습 카드 리스트를 조회한다
  public List<ReviewCard> getCardsByUserId(Long userId) {
    return reviewCardRepository.findByUserId(userId);
  }





//
//  // 카드 리스트를 조회한다
//  public List<ReviewCard> getCardsByDeck(Long deckId) {
//    return cardRepository.findByDeckId(deckId);
//  }

  // 카드를 조회한다
  public ReviewCard getCardById(Long cardId) {
    return reviewCardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card를 찾을 수 없습니다"));
  }

  // 카드 내용을 수정한다
  public ReviewCard updateCard(Long cardId, String term, String definition, String exampleSentence) {
    ReviewCard card = reviewCardRepository.findById(cardId)
      .orElseThrow(() -> new RuntimeException("Card를 찾을 수 없습니다"));

    card.setTerm(term);
    card.setDefinition(definition);
    card.setExampleSentence(exampleSentence);
    return reviewCardRepository.save(card);
  }

  // 카드를 삭제한다
  public boolean deleteCard(Long cardId) {
    if (reviewCardRepository.existsById(cardId)) {
      reviewCardRepository.deleteById(cardId);
      return true;
    } else {
      return false;
    }
  }
}