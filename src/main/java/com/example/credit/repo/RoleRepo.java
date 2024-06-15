package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.RoleEntity;

/**
 * RoleRepo
 */
public interface RoleRepo extends JpaRepository<RoleEntity,Integer>{

    RoleEntity findByRoleName(String rolename);
}
