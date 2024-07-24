package com.example.credit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.credit.entities.FriendReq_Receiver;

/**
 * FriendReq_RecRepo
 */
public interface FriendReq_RecRepo extends JpaRepository<FriendReq_Receiver,Integer>{

    
}
