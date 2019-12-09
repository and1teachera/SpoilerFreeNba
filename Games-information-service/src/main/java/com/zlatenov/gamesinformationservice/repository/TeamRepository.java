package com.zlatenov.gamesinformationservice.repository;

import com.zlatenov.gamesinformationservice.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface TeamRepository extends JpaRepository<Team, String> {
}
