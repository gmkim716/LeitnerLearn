//package com.TMDB.backend.Service;
//
//import com.TMDB.backend.Entity.Deck;
//import com.TMDB.backend.Entity.User;
//import com.TMDB.backend.Repository.DeckRepository;
//import com.TMDB.backend.Repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DeckServiceTest {
//
//  @Mock
//  private DeckRepository deckRepository;
//
//  @Mock
//  private UserRepository userRepository;
//
//  @InjectMocks
//  private DeckService deckService;
//
//  @Test
//  void testCreateDeck() {
//    // 테스트용 유저 ID와 덱 이름을 설정합니다.
//    Long userId = 1L;
//    String deckName = "Test Deck";
//
//    // 유저와 덱을 생성합니다.
//    User mockUser = new User();
//    mockUser.setId(userId);
//
//    // userRepository.findById()가 호출될 때 mockUser를 반환하도록 설정합니다.
//    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
//
//    // 덱을 생성합니다.
//    Deck mockDeck = Deck.builder().name(deckName).user(mockUser).build();
//    when(deckRepository.save(any(Deck.class))).thenReturn(mockDeck);
//
//    Deck createdDeck = deckService.createDeck(userId, deckName);
//
//    // 생성된 덱이 null이 아니고, 이름과 유저가 올바른지 확인합니다.
//    assertNotNull(createdDeck);
//    assertEquals(deckName, createdDeck.getName());
//    assertEquals(mockUser, createdDeck.getUser());
//
//    // userRepository.findById()와 deckRepository.save()가 각각 한 번씩 호출되었는지 확인합니다.
//    verify(userRepository, times(1)).findById(userId);
//    verify(deckRepository, times(1)).save(any(Deck.class));
//  }
//
//  @Test
//  void testGetAllDecksByUserId() {
//    // 테스트 용 유저 ID를 설정합니다.
//    Long userId = 1L;
//
//    // 덱을 생성합니다.
//    Deck deck1 = new Deck();
//    Deck deck2 = new Deck();
//    List<Deck> mockDecks = Arrays.asList(deck1, deck2);
//
//  }
//
//  @Test
//  void testGetDeckById() {
//    // 테스트용 덱 ID를 설정합니다.
//    Long deckId = 1L;
//
//    // 덱을 생성합니다.
//    Deck mockDeck = new Deck();
//    mockDeck.setId(deckId);
//
//    // deckRepository.findById()가 호출될 때 mockDeck을 반환하도록 설정합니다.
//    when(deckRepository.findById(deckId)).thenReturn(Optional.of(mockDeck));
//
//    // 덱 ID로 덱을 조회합니다.
//    Deck deck = deckService.getDeckById(deckId);
//
//    // 조회된 덱이 null이 아니고, ID가 올바른지 확인합니다.
//    assertNotNull(deck);
//    assertEquals(deckId, deck.getId());
//    verify(deckRepository, times(1)).findById(deckId);
//  }
//
//  @Test
//  void testUpdateDeck() {
//    // 테스트용 덱 ID와 새로운 덱 이름을 설정합니다.
//    Long deckId = 1L;
//    String newDeckName = "Updated Deck";
//
//    // 덱을 생성합니다.
//    Deck mockDeck = new Deck();
//    mockDeck.setId(deckId);
//    mockDeck.setName("Old Deck");
//
//    // deckRepository.findById()가 호출될 때 mockDeck을 반환하도록 설정합니다.
//    when(deckRepository.findById(deckId)).thenReturn(Optional.of(mockDeck));
//    when(deckRepository.save(mockDeck)).thenReturn(mockDeck);
//
//    // 덱 이름을 업데이트합니다.
//    Deck updatedDeck = deckService.updateDeck(deckId, newDeckName);
//
//    // 업데이트된 덱이 null이 아니고, 이름이 올바른지 확인합니다.
//    assertNotNull(updatedDeck);
//    assertEquals(newDeckName, updatedDeck.getName());
//    verify(deckRepository, times(1)).findById(deckId);
//    verify(deckRepository, times(1)).save(mockDeck);
//  }
//
//  @Test
//  void testDeleteDeck() {
//    // 테스트용 덱 ID를 설정합니다.
//    Long deckId = 1L;
//
//    // 덱을 생성합니다.
//    Deck mockDeck = new Deck();
//    mockDeck.setId(deckId);
//
//    // deckRepository.findById()가 호출될 때 mockDeck을 반환하도록 설정합니다.
//    when(deckRepository.findById(deckId)).thenReturn(Optional.of(mockDeck));
//
//    // 덱을 삭제합니다.
//    boolean result = deckService.deleteDeck(deckId);
//
//    // 삭제 결과가 true인지 확인합니다.
//    assertTrue(result);
//    verify(deckRepository, times(1)).findById(deckId);
//    verify(deckRepository, times(1)).delete(mockDeck);
//  }
//}