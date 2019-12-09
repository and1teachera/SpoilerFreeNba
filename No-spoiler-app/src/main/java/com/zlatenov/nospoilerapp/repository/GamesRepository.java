package com.zlatenov.nospoilerapp.repository;

import com.zlatenov.nospoilerapp.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface GamesRepository extends JpaRepository<Game, String> {
}
