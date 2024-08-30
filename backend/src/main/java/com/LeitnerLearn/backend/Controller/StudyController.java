package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Dto.LearningCardDto;
import com.LeitnerLearn.backend.Dto.LearningStatsDto;
import com.LeitnerLearn.backend.Dto.StarterCardsDto;
import com.LeitnerLearn.backend.Service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
@Tag(name = "Study API", description = "학습 관련 API를 제공합니다.")
public class StudyController {

  private final StudyService studyService;

  @Operation(summary = "카드 학습에 대한 정답/오답을 기록합니다.", description = "특정 카드에 대해 사용자가 정답을 맞췄는지 여부를 기록합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "복습 결과가 성공적으로 기록되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @PostMapping("/card/update-result/{cardId}/{userId}")
  public ResponseEntity<Void> updateLearningCardResult(
    @Parameter(description = "학습/복습한 카드의 ID", required = true) @PathVariable Long cardId,
    @Parameter(description = "사용자의 ID", required = true) @PathVariable Long userId,
    @Parameter(description = "학습 결과가 정답이면 true, 오답이면 false", required = true) @RequestParam boolean correct) {
    studyService.updateLearningCardResult(cardId, userId, correct);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "사용자를 위한 학습 목록을 생성합니다", description = "특정 사용자를 위한 학습 목록을 생성합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "학습 목록이 성공적으로 생성되었습니다."),
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
  })
  @GetMapping("/make-learning-cards/{userId}")
  public ResponseEntity<LearningCardDto> makeLearningCards(
    @Parameter(description = "사용자 ID", required = true) @PathVariable Long userId) {
    LearningCardDto cards = studyService.makeLearningCards(userId);
    return ResponseEntity.ok(cards);
  }

  @Operation(summary = "특정 사용자의 학습 목록 통계를 조회합니다.", description = "난이도별 학습 목록과 개수를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "학습 목록이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
  })
  @GetMapping("/stats/{userId}")
  public ResponseEntity<LearningStatsDto> getLearningStats(
    @Parameter(description = "사용자 ID", required = true) @PathVariable Long userId) {
    LearningStatsDto stats = studyService.getLearningStats(userId);
    return ResponseEntity.ok(stats);
  }

  @Operation(summary = "필수 학습카드 이수 여부를 확인합니다", description = "필수 학습 카드 중 학습하지 않은 카드 리스트를 반환합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "필수 학습카드를 모두 이수했습니다."),
    @ApiResponse(responseCode = "404", description = "필수 학습카드를 이수하지 못했습니다.")
  })
  @GetMapping("/cards/must-complete/{userId}")
  public ResponseEntity<StarterCardsDto> getStarterCards(
    @Parameter(description = "사용자 ID", required = true) @PathVariable Long userId) {
    StarterCardsDto cards = studyService.getStarterCards(userId);
    return ResponseEntity.ok(cards);
  }
}