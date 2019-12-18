package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.Standings;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface StandingsRepository extends JpaRepository<Standings, String> {

    List<Standings> findByDate(Date date);

    Standings findByTeamAndDate(Team team, Date date);
}
