package com.LeitnerLearn.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "boxes")
public class Box {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int boxNumber;

  @Column(nullable = false)
  private int reviewIntervalDays;  // Box에서 복습 간격 (일 단위)

  @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Card> cards = new ArrayList<>();

}
