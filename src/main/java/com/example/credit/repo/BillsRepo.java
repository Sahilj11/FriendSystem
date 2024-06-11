package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.BillEntity;

/**
 * BillsRepo
 */
public interface BillsRepo extends JpaRepository<BillEntity,Integer>{
}
