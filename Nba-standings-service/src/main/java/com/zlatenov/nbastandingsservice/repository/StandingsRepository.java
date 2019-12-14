package com.zlatenov.nbastandingsservice.repository;

import com.zlatenov.nbastandingsservice.model.entity.Standings;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */
public interface StandingsRepository extends CrudRepository<Standings, String> {

    List<Standings> findByDate(Date date);
}
