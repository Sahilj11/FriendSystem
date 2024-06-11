package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.UserEntity;

/**
 * UserRepo
 */
public interface UserRepo extends JpaRepository<UserEntity,Integer>{

}
