package com.LeitnerLearn.backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private boolean notificationEnabled;

  @Column(nullable = false)
  private boolean darkModeEnabled;

}
