package com.zlatenov.nospoilerapp.repository;

import com.zlatenov.nospoilerapp.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface TeamRepository extends JpaRepository<Team, String> {
}
