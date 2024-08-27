package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Entity.Card;
import com.LeitnerLearn.backend.Entity.Deck;
import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import com.LeitnerLearn.backend.Entity.GlobalLearningCard;
import com.LeitnerLearn.backend.Service.GlobalLearningCardService;
import com.LeitnerLearn.backend.Service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
@Tag(name = "Study API", description = "학습 관련 API를 제공합니다.")
public class StudyController {

  private final StudyService studyService;
  private final GlobalLearningCardService globalLearningCardService;

  @Operation(summary = "사용자에게 자동으로 덱을 할당합니다.", description = "난이도 평가 결과에 따라 적절한 덱과 단어를 자동으로 할당합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱이 성공적으로 할당되었습니다."),
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
  })
  @PostMapping("/assign-deck")
  public ResponseEntity<Deck> assignDeckBasedOnDifficulty(
    @Parameter(description = "덱을 할당할 사용자의 ID", required = true) @RequestParam Long userId,
    @Parameter(description = "평가된 난이도 (예: Beginner, Intermediate, Advanced)", required = true) @RequestParam String difficulty) {
    Deck deck = studyService.assignDeckBasedOnDifficulty(userId, difficulty);
    return ResponseEntity.ok(deck);
  }

//  @Operation(summary = "전체 복습 카드 목록을 조회합니다.", description = "특정 덱에서 복습이 필요한 카드 목록을 가져옵니다.")
//  @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "복습할 카드 목록이 성공적으로 반환되었습니다."),
//    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
//  })
//  @GetMapping("/decks/{deckId}/cards")
//  public ResponseEntity<List<Card>> getCardsForReview(
//    @Parameter(description = "복습할 덱의 ID", required = true) @PathVariable Long deckId) {
//    List<Card> cards = studyService.getCardsForReview(deckId);
//    return ResponseEntity.ok(cards);
//  }

    @Operation(summary = "카드 복습 정답/오답을 기록합니다.", description = "특정 카드에 대해 사용자가 정답을 맞췄는지 여부를 기록합니다.")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "복습 결과가 성공적으로 기록되었습니다."),
      @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
    })
    @PostMapping("/card/{cardId}/review")
    public ResponseEntity<Void> reviewCard(
      @Parameter(description = "복습한 카드의 ID", required = true) @PathVariable Long cardId,
      @Parameter(description = "복습 결과가 정답이면 true, 오답이면 false", required = true) @RequestParam boolean correct) {
      studyService.reviewCard(cardId, correct);
      return ResponseEntity.ok().build();
    }

  @Operation(summary = "덱의 학습 상태를 조회합니다.", description = "특정 덱의 복습 상태(복습이 필요한 카드 수)를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱의 학습 상태가 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
  })
  @GetMapping("/decks/{deckId}/status")
  public ResponseEntity<String> getDeckStudyStatus(
    @Parameter(description = "학습 상태를 조회할 덱의 ID", required = true) @PathVariable Long deckId) {
    String status = studyService.getStudyStatus(deckId);
    return ResponseEntity.ok(status);
  }

  @Operation(summary = "복습 가능한 카드 목록을 조회합니다.", description = "특정 사용자가 복습할 수 있는 카드 목록을 가져옵니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "복습 가능한 카드 목록이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
  })
  @GetMapping("/deck/review/{deckId}")
  public ResponseEntity<List<Card>> getReviewCards(
    @Parameter(description = "덱 ID", required = true) @PathVariable Long deckId) {
    List<Card> cards = studyService.getReviewCards(deckId);
    return ResponseEntity.ok(cards);
  }

  // step2. 학습 레벨에 해당하는 count 개의 데이터를 가져온다
  @GetMapping("/global-learning-cards/{levelCode}/{count}")
  public ResponseEntity<List<GlobalLearningCard>> getGlobalLearningCardsByDifficultyLevel(
    @PathVariable DifficultyLevel levelCode,
    @PathVariable int count) {
    List<GlobalLearningCard> globalLearningCards = studyService.getGlobalLearningCardsByDifficultyLevel(levelCode, count);
    return ResponseEntity.ok(globalLearningCards);
  }

  // stepUp. 학습을 위한 카드 목록을 생성한다
  @GetMapping("/make-learning-cards/{deckId}/{levelCode}/{size}")
  public ResponseEntity<List<?>> makeLearningCards(
    @PathVariable Long deckId,  // 특정 사용자의 복습용 덱
    @PathVariable DifficultyLevel levelCode,  // 학습 난이도
    @PathVariable int size) {  // 개수 설정
    List<?> cards = studyService.makeLearningCards(deckId, levelCode, size);
    return ResponseEntity.ok(cards);
  }


//  @Operation(summary = "복습 가능한 카드 목록 + 새로운 문제를 추가해서 30개의 학습 목록을 생성합니다.", description = "특정 사용자가 복습할 수 있는 카드 목록을 가져옵니다.")
//  @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "복습 가능한 카드 목록이 성공적으로 반환되었습니다."),
//    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
//  })
//  @GetMapping("/deck/make-learning-card-set/{deckId}")
//  public ResponseEntity<List<Card>> makeLearningSet(
//    @Parameter(description = "덱 ID", required = true) @PathVariable Long deckId) {
//    List<Card> cards = studyService.makeLearningCards(deckId);
//    return ResponseEntity.ok(cards);
//  }


//  // 학습 난이도에 따른 전역 학습 카드 목록을 조회한다
//  @Operation(summary = "난이도에 따른 전역 학습 카드 목록을 조회합니다.", description = "난이도에 따른 전역 학습 카드 목록을 가져옵니다.")
//  @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "복습 가능한 카드 목록이 성공적으로 반환되었습니다."),
//    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
//  })
//  @GetMapping("/global-learning-cards/{levelCode}")
//  public ResponseEntity<List<GlobalLearningCard>> getGlobalLearningCarsByDifficultyLevel(
//    @Parameter(description = "난이도", required = true) @PathVariable DifficultyLevel levelCode) {
//    List<GlobalLearningCard> globalLearningCards = globalLearningCardService.getGlobalLearningCardsByDifficultyLevel(levelCode);
//    return ResponseEntity.ok(globalLearningCards);
//  }

//  // 사용자의 복습 덱 + 새로운 문제를 추가해서 자동 문제를 제공한다
//  @Operation(summary = "자동으로 문제를 추가합니다.", description = "특정 덱에 자동으로 문제를 추가합니다.")
//  @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "문제가 성공적으로 추가되었습니다."),
//    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
//  })
//  @PostMapping("/deck/make-learning-deck/{deckId}")
//  public ResponseEntity<Deck> makeLearningDeck(
//    @Parameter(description = "문제를 추가할 덱의 ID", required = true) @PathVariable Long deckId) {
//    Deck deck = studyService.makeLearningDeckWithDeckId(deckId);
//    return ResponseEntity.ok(deck);
//  }

}