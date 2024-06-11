package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.ConnectionEntity;

/**
 * ConnectionRepo
 */
public interface ConnectionRepo extends JpaRepository<ConnectionEntity,Integer>{

}
