package com.LeitnerLearn.backend.Entity.common;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@Getter @Setter @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
public class BaseCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String term;

  @Column(nullable = false)
  private String definition;

  @Column(nullable = false)
  private String exampleSentence;

  @Column(nullable = false) @Enumerated(EnumType.STRING)
  private DifficultyLevel difficultyLevel;

}
