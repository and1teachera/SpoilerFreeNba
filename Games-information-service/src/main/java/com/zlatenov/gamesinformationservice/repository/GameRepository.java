package com.zlatenov.gamesinformationservice.repository;

import com.zlatenov.gamesinformationservice.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface GameRepository extends JpaRepository<Game, String> {
}
