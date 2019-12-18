package com.zlatenov.teamsinformationservice.repository;

import com.zlatenov.teamsinformationservice.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface PlayerRepository extends JpaRepository<Player, String> {
}