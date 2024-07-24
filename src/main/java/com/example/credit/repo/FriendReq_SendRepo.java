package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.FriendReq_Sender;

/**
 * FriendReq_SendRepo
 */
public interface FriendReq_SendRepo extends JpaRepository<FriendReq_Sender,Integer>{
}
