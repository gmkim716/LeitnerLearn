package com.LeitnerLearn.backend.Controller;

import com.LeitnerLearn.backend.Dto.UserRequestDto;
import com.LeitnerLearn.backend.Dto.UserResponseDto;
import com.LeitnerLearn.backend.Entity.User;
import com.LeitnerLearn.backend.Service.UserService;
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
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "사용자 관리와 관련된 API입니다.")
public class UserController {

  private final UserService userService;

  @Operation(summary = "모든 사용자 목록을 조회합니다.", description = "시스템에 등록된 모든 사용자를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "사용자 목록이 성공적으로 반환되었습니다.")
  })
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @Operation(summary = "ID로 사용자를 조회합니다.", description = "특정 ID를 가진 사용자를 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 반환되었습니다."),
    @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.")
  })
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(
    @Parameter(description = "조회할 사용자의 ID", required = true) @PathVariable Long id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @Operation(summary = "새 사용자를 등록합니다.", description = "새로운 사용자를 등록합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "사용자가 성공적으로 등록되었습니다."),
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
  })
  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> registerUser(
    @Parameter(description = "등록할 사용자 정보", required = true) @RequestBody UserRequestDto user) {
    return userService.registerUser(user);
  }

  @Operation(summary = "사용자 정보를 수정합니다.", description = "특정 사용자의 정보를 수정합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "사용자 정보가 성공적으로 수정되었습니다."),
    @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.")
  })
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(
    @Parameter(description = "수정할 사용자의 ID", required = true) @PathVariable Long id,
    @Parameter(description = "수정할 사용자 정보", required = true) @RequestBody UserRequestDto user) {
    return ResponseEntity.ok(userService.updateUser(id, user));
  }

  @Operation(summary = "사용자를 삭제합니다.", description = "특정 사용자를 삭제합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 삭제되었습니다."),
    @ApiResponse(responseCode = "404", description = "해당 ID의 사용자를 찾을 수 없습니다.")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(
    @Parameter(description = "삭제할 사용자의 ID", required = true) @PathVariable Long id) {
    if (userService.deleteUser(id)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}