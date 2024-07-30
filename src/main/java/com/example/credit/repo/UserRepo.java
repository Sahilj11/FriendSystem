package com.example.credit.repo;

import com.example.credit.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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

    @Query("SELECT p FROM UserEntity p WHERE p.name LIKE :name%")
    List<UserEntity> findByNameLike(@Param("name") String name,Pageable pageable);

    Optional<UserEntity> findByUserId(int uid);
}
