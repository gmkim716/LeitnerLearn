package com.TMDB.backend.Repository;

import com.TMDB.backend.Entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {
  Optional<Box> findByBoxNumber(int boxNumber);
}
