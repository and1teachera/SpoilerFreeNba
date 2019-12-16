package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GamesRepository extends JpaRepository<Game, String> {

    List<Game> findAllByStartTimeUtcBetween(Date startDateUtc, Date endDateUtc);
}
