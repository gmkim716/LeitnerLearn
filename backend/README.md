### Deck 테이블 삭제 (8/27)
- 한 명의 유저(User)가 여러 학습 목록(Deck)을 가질 수 있도록 구현하려던 방식에서, 하나의 학습 목록만 가질 수 있도록 변경합니다.
- 기존의 Deck 테이블을 삭제하고, reviewCard 테이블의 deck_id를 user_id로 변경합니다.
- 프로젝트 구현 목적에 보다 적합하고, 간단한 구조로 변경합니다.

### 구현에 반영 사항 추가
- 모든 사용자는 가입 후, 일정 기간 동안 기본적인 재정 교육을 수료해야 함
- 사용자의 학습 레벨에 맞는 학습 카드를 제공, 정답률이 70% 이상이면 한 단계 위의 컨텐츠 접근 가능 




---
# 구현 과정에서 배운 점

### @ElementCollection, @Transient
- @ElementCollection: 엔티티 내에서 기본 값 또는 다른 엔티티와의 관계가 아닌 값 타입 컬렉션을 매핑할 때 사용한다.
    
  - JPA는 해당 필드를 위한 별도의 테이블을 생성한다. 이 테이블은 주 엔티티의 Id와 컬렉션 요소를 매핑한다
  - 기본 타입이나 임베디드 타입의 컬렉션을 에티티와 함께 저장하고자 할 때 사용한다
 
- @Transient: JPA가 해당 필드를 데이터베이스에 매핑하지 않도록 지정한다
    
  - 계산된 필드나 임시 데이터를 저장할 때 사용한다

### 조건문에 삼항 연산자를 사용하는 방법
- 삼항연산자를 활용해 조건문을 간소화할 수 있다
- 클래스의 메서드를 실행할 때는 삼항연산자를 사용하지 않고 케이스를 구분하도록 한다
```java
//    LocalDateTime nextReviewAt;
//    if (boxNumber == 5) { 
//      nextReviewAt = LocalDateTime.now().plusDays(9999);
//    } else {
//      nextReviewAt = LocalDateTime.now();
//    }
    LocalDateTime nextReviewAt = (boxNumber == 5) ? LocalDateTime.now().plusDays(9999) : LocalDateTime.now();
```