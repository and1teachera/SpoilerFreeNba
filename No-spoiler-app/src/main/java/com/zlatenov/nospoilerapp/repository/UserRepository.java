package com.zlatenov.nospoilerapp.repository;

import com.zlatenov.nospoilerapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface UserRepository extends JpaRepository<User, String> {
}
