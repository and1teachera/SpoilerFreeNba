package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface GamesRepository extends JpaRepository<Game, String> {

    List<Game> findAllByStartTimeUtcBetween(Date startDateUtc, Date endDateUtc);

    List<Game> findAllByStartTimeUtcBefore(Date startDateUtc);

    List<Game> findByHomeTeamOrAwayTeam(Team team, Team team2);

    Game findByHomeTeamAndAwayTeamAndStartTimeUtc(Team homeTeam, Team awayTeam, Date date);

    List<Game> findByStartTimeUtc(Date date);
}
