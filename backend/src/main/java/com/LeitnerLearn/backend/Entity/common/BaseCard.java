package com.LeitnerLearn.backend.Entity.common;

import com.LeitnerLearn.backend.Entity.DifficultyLevel;
import com.LeitnerLearn.backend.Entity.Tag;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


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

  @ManyToMany
  @JoinTable(
    name = "card_tags",
    joinColumns = @JoinColumn(name = "card_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

}
