package com.example.credit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.UserEntity;

/**
 * UserRepo
 */
public interface UserRepo extends JpaRepository<UserEntity,Integer>{

    Optional<UserEntity> findByUniquename(String username);
}
