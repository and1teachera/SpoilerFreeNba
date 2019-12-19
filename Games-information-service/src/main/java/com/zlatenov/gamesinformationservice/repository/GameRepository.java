package com.zlatenov.gamesinformationservice.repository;

import com.zlatenov.gamesinformationservice.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GameRepository extends JpaRepository<Game, String> {

    List<Game> findGamesByStartTimeUtc(Date date);
}
