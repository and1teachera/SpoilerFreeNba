package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
