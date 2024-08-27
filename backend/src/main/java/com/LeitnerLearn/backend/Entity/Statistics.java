package com.LeitnerLearn.backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stastics")
public class Statistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private int totalCards;

  @Column(nullable = false)
  private int correctAnswers;

  @Column(nullable = false)
  private int inCorrectAnswers;

  @Column(nullable = false)
  private LocalDateTime lastUpdated;
}
