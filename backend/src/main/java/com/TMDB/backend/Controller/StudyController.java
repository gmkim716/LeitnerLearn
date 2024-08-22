package com.TMDB.backend.Controller;

import com.TMDB.backend.Entity.Card;
import com.TMDB.backend.Entity.Deck;
import com.TMDB.backend.Service.StudyService;
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

  @Operation(summary = "복습할 카드 목록을 조회합니다.", description = "특정 덱에서 복습이 필요한 카드 목록을 가져옵니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "복습할 카드 목록이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
  })
  @GetMapping("/decks/{deckId}/cards")
  public ResponseEntity<List<Card>> getCardsForReview(
    @Parameter(description = "복습할 덱의 ID", required = true) @PathVariable Long deckId) {
    List<Card> cards = studyService.getCardsForReview(deckId);
    return ResponseEntity.ok(cards);
  }

  @Operation(summary = "카드 복습 결과를 기록합니다.", description = "특정 카드에 대해 사용자가 정답을 맞췄는지 여부를 기록합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "복습 결과가 성공적으로 기록되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @PostMapping("/cards/{cardId}/review")
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
}