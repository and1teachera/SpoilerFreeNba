package com.zlatenov.nbastandingsservice.repository;

import com.zlatenov.nbastandingsservice.model.entity.Standings;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Angel Zlatenov
 */
public interface StandingsRepository extends CrudRepository<Standings, String> {
}
