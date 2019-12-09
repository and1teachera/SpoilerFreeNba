package com.zlatenov.userauthorisationservice.repository;

import com.zlatenov.userauthorisationservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);
}
