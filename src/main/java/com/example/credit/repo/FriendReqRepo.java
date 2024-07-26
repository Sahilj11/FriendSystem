package com.example.credit.repo;

import com.example.credit.entities.Friend_request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendReqRepo extends JpaRepository<Friend_request,Integer> {
    boolean existsByUid1(int uid1);
    boolean existsByUid2(int uid2);
}