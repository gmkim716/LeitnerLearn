package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Entity.ReviewCard;
import com.LeitnerLearn.backend.Service.CardService;
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
public class CardController {

  private final CardService cardService;

  @Operation(summary = "새로운 카드를 생성합니다.", description = "특정 덱에 새로운 카드를 추가합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 생성되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
  })
  @PostMapping("/create")
  public ResponseEntity<ReviewCard> createCard(
    @Parameter(description = "카드를 추가할 덱의 ID", required = true) @RequestParam Long deckId,
    @Parameter(description = "카드의 용어", required = true) @RequestParam String term,
    @Parameter(description = "카드의 정의", required = true) @RequestParam String definition,
    @Parameter(description = "카드의 예문") @RequestParam(required = false) String exampleSentence) {
    ReviewCard card = cardService.createCard(deckId, term, definition, exampleSentence);
    return ResponseEntity.ok(card);
  }

  @Operation(summary = "특정 덱의 모든 카드를 조회합니다.", description = "특정 덱에 포함된 모든 카드를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드 목록이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.")
  })
  @GetMapping("/deck/{deckId}")
  public ResponseEntity<List<ReviewCard>> getCardsByDeck(
    @Parameter(description = "카드를 조회할 덱의 ID", required = true) @PathVariable Long deckId) {
    List<ReviewCard> cards = cardService.getCardsByDeck(deckId);
    return ResponseEntity.ok(cards);
  }

  @Operation(summary = "특정 카드를 조회합니다.", description = "카드의 ID를 사용하여 특정 카드를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @GetMapping("/{cardId}")
  public ResponseEntity<ReviewCard> getCardById(
    @Parameter(description = "조회할 카드의 ID", required = true) @PathVariable Long cardId) {
    ReviewCard card = cardService.getCardById(cardId);
    return ResponseEntity.ok(card);
  }

  @Operation(summary = "카드를 수정합니다.", description = "특정 카드를 수정합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 수정되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @PutMapping("/{cardId}")
  public ResponseEntity<ReviewCard> updateCard(
    @Parameter(description = "수정할 카드의 ID", required = true) @PathVariable Long cardId,
    @Parameter(description = "카드의 용어", required = true) @RequestParam String term,
    @Parameter(description = "카드의 정의", required = true) @RequestParam String definition,
    @Parameter(description = "카드의 예문") @RequestParam(required = false) String exampleSentence) {
    ReviewCard updatedCard = cardService.updateCard(cardId, term, definition, exampleSentence);
    return ResponseEntity.ok(updatedCard);
  }

  @Operation(summary = "카드를 삭제합니다.", description = "특정 카드를 삭제합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "카드가 성공적으로 삭제되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.")
  })
  @DeleteMapping("/{cardId}")
  public ResponseEntity<Void> deleteCard(
    @Parameter(description = "삭제할 카드의 ID", required = true) @PathVariable Long cardId) {
    if (cardService.deleteCard(cardId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{cardId}/box")
  @Operation(summary = "카드의 현재 박스 번호를 조회합니다.", description = "카드가 현재 몇 번째 박스에 위치해 있는지 확인합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "박스 번호가 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "카드를 찾을 수 없습니다.") })
  public ResponseEntity<Integer> getCardBoxNumber( @Parameter(description = "박스 번호를 조회할 카드의 ID", required = true) @PathVariable Long cardId) {
    ReviewCard card = cardService.getCardById(cardId);
    return ResponseEntity.ok(card.getBox().getBoxNumber());
  }
}