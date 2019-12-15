package com.zlatenov.userauthorisationservice.repository;

import com.zlatenov.userauthorisationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
