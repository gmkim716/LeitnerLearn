package com.TMDB.backend.Controller;

import com.TMDB.backend.Dto.DeckDto;
import com.TMDB.backend.Entity.Deck;
import com.TMDB.backend.Service.DeckService;
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
@RequestMapping("/api/decks")
@Tag(name = "Deck API", description = "덱 관리와 관련된 API입니다.")
public class DeckController {

  private final DeckService deckService;

  @Operation(summary = "새로운 덱을 생성합니다.", description = "사용자의 ID와 덱 이름을 사용하여 새로운 덱을 생성합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱이 성공적으로 생성되었습니다."),
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.") })
  @PostMapping("/create")
  public ResponseEntity<DeckDto> createDeck(
    @Parameter(description = "덱을 생성할 사용자의 ID", required = true) @RequestParam Long userId,
    @Parameter(description = "생성할 덱의 이름", required = true) @RequestParam String deckName) {
    return ResponseEntity.ok(deckService.createDeck(userId, deckName));
  }

  @Operation(summary = "사용자의 모든 덱을 조회합니다.", description = "특정 사용자가 소유한 모든 덱을 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱 목록이 성공적으로 반환되었습니다.") })
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<DeckDto>> getAllDecksByUser( @Parameter(description = "덱을 조회할 사용자의 ID", required = true) @PathVariable Long userId) {
    return ResponseEntity.ok(deckService.getAllDecksByUserId(userId));
  }

  @Operation(summary = "특정 덱을 조회합니다.", description = "덱의 ID를 사용하여 특정 덱을 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱이 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.") })
  @GetMapping("/{deckId}")
  public ResponseEntity<Deck> getDeckById( @Parameter(description = "조회할 덱의 ID", required = true) @PathVariable Long deckId) {
    Deck deck = deckService.getDeckById(deckId);
    return ResponseEntity.ok(deck);
  }

  @Operation(summary = "덱의 이름을 수정합니다.", description = "특정 덱의 이름을 수정합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱이 성공적으로 수정되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.") })
  @PutMapping("/{deckId}")
  public ResponseEntity<Deck> updateDeck(
    @Parameter(description = "수정할 덱의 ID", required = true) @PathVariable Long deckId,
    @Parameter(description = "새로운 덱 이름", required = true) @RequestParam String deckName) {
    Deck updatedDeck = deckService.updateDeck(deckId, deckName);
    return ResponseEntity.ok(updatedDeck);
  }

  @Operation(summary = "덱을 삭제합니다.", description = "특정 덱을 삭제합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "덱이 성공적으로 삭제되었습니다."),
    @ApiResponse(responseCode = "404", description = "덱을 찾을 수 없습니다.") })
  @DeleteMapping("/{deckId}")
  public ResponseEntity<Void> deleteDeck(
    @Parameter(description = "삭제할 덱의 ID", required = true) @PathVariable Long deckId) {
    if (deckService.deleteDeck(deckId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
