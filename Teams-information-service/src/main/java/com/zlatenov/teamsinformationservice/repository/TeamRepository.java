package com.zlatenov.teamsinformationservice.repository;

import com.zlatenov.teamsinformationservice.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface TeamRepository extends JpaRepository<Team, String> {
}
