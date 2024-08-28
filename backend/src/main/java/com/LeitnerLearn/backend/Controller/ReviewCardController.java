package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Dto.CreateCardRequestDto;
import com.LeitnerLearn.backend.Entity.ReviewCard;
import com.LeitnerLearn.backend.Service.ReviewCardService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
@Tag(name = "Card API", description = "카드 관리와 관련된 API입니다.")
public class ReviewCardController {

  private final ReviewCardService reviewCardService;

  @Operation(summary = "새로운 카드를 생성합니다.", description = "새로운 카드를 추가합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 생성되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
  })
  @PostMapping("/create")
  public ResponseEntity<ReviewCard> createCard(
    @RequestBody CreateCardRequestDto request) {
    ReviewCard card = reviewCardService.createCard(request);
    return ResponseEntity.ok(card);
  }

  @Operation(summary = "특정 사용자의 모든 복습 카드를 조회합니다.", description = "특정 사용자의 모든 복습 카드를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "복습 카드 목록이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "복습 카드 목록을 찾을 수 없습니다.")
  })
  @GetMapping("/review/{userId}")
  public ResponseEntity<List<ReviewCard>> getCardsByUserId(
    @Parameter(description = "카드를 조회할 사용자의 ID", required = true) @PathVariable Long userId) {
    List<ReviewCard> cards = reviewCardService.getCardsByUserId(userId);
    return ResponseEntity.ok(cards);
  }

  @Operation(summary = "특정 복습 카드를 조회합니다.", description = "카드의 ID를 사용하여 특정 복습 카드를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @GetMapping("/{cardId}")
  public ResponseEntity<ReviewCard> getCardById(
    @Parameter(description = "조회할 카드의 ID", required = true) @PathVariable Long cardId) {
    ReviewCard card = reviewCardService.getCardById(cardId);
    return ResponseEntity.ok(card);
  }
}