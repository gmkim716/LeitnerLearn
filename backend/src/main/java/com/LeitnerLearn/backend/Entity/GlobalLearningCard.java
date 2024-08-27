package com.LeitnerLearn.backend.Entity;

import com.LeitnerLearn.backend.Entity.common.BaseCard;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@Getter @Setter @SuperBuilder
@Table(name = "global_learning_cards")
public class GlobalLearningCard extends BaseCard {

}
