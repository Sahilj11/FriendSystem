package com.example.credit.repo;

import com.example.credit.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepo
 *
 * <h2>Methods</h2>
 *
 * <ul>
 * <li>findByEmail
 * </ul>
 */
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
